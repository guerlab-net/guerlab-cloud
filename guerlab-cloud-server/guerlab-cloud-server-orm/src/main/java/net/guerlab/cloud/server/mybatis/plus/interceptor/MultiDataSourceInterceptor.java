/*
 * Copyright 2018-2025 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.server.mybatis.plus.interceptor;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.ConcurrentReferenceHashMap;

import net.guerlab.cloud.server.jdbc.DataSourceContextHolder;
import net.guerlab.cloud.server.jdbc.PointcutProperties;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 多数据源拦截器.
 *
 * @author guer
 */
@Slf4j
public class MultiDataSourceInterceptor implements InnerInterceptor {

	private final Map<String, PointcutProperties> matchCache = new ConcurrentReferenceHashMap<>();

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 数据源上下文保持.
	 */
	private final DataSourceContextHolder dataSourceContextHolder;

	/**
	 * 动态数据源切面配置列表.
	 */
	private final List<PointcutProperties> advisorPropertiesList;

	/**
	 * 创建多数据源拦截器.
	 *
	 * @param dataSourceContextHolder 数据源上下文保持
	 * @param advisorPropertiesList   动态数据源切面配置列表
	 */
	public MultiDataSourceInterceptor(DataSourceContextHolder dataSourceContextHolder, List<PointcutProperties> advisorPropertiesList) {
		this.dataSourceContextHolder = dataSourceContextHolder;
		this.advisorPropertiesList = advisorPropertiesList;
	}

	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
		selectDataSourceHandler(ms);
	}

	@Override
	public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) {
		selectDataSourceHandler(ms);
	}

	@Override
	public void beforeGetBoundSql(StatementHandler sh) {
		InnerInterceptor.super.beforeGetBoundSql(sh);
	}

	@Override
	public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
		InnerInterceptor.super.beforePrepare(sh, connection, transactionTimeout);
	}

	private void selectDataSourceHandler(MappedStatement ms) {
		dataSourceContextHolder.clearDataSource();

		String id = ms.getId();
		PointcutProperties cache = matchCache.get(id);
		if (cache == null) {
			int index = id.lastIndexOf(".");
			if (index <= 0) {
				log.trace("cannot parse mybatis mapper namespace");
				return;
			}

			String className = id.substring(0, index);
			String methodName = id.substring(index + 1);

			cache = advisorPropertiesList.stream()
					.map(properties -> match(className, methodName, properties))
					.filter(MatchResult::anyMath)
					.sorted().findFirst().map(MatchResult::properties).orElse(null);

			if (cache != null) {
				log.debug("matchResult: {}", cache);
				matchCache.put(id, cache);
			}
		}

		if (cache != null) {
			dataSourceContextHolder.setDataSource(cache.getDataSource());
		}
	}

	private MatchResult match(String className, String methodName, PointcutProperties properties) {
		List<String> classNameMatchList = CollectionUtil.toDistinctList(properties.getClassNameMatch(), Function.identity());
		List<String> methodNameMatchList = CollectionUtil.toDistinctList(properties.getMethodNameMatch(), Function.identity());

		return new MatchResult(
				classNameMatch(className, classNameMatchList),
				methodNameMatch(methodName, methodNameMatchList),
				properties
		);
	}

	private boolean classNameMatch(String className, List<String> list) {
		if (list.isEmpty()) {
			return false;
		}

		String classPath = formatPath(className);

		return list.stream().map(this::formatPath).anyMatch(path -> pathMatcher.match(path, classPath));
	}

	private String formatPath(String name) {
		return name.replace('.', '/');
	}

	private boolean methodNameMatch(String methodName, List<String> list) {
		if (list.isEmpty()) {
			return false;
		}

		return list.stream().anyMatch(methodName::matches);
	}

}
