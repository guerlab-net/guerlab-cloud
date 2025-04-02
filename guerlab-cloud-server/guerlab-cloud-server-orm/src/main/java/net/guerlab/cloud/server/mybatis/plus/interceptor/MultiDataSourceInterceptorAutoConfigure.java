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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.server.jdbc.DataSourceContextHolder;
import net.guerlab.cloud.server.jdbc.MultiDataSourceProperties;
import net.guerlab.cloud.server.jdbc.PointcutProperties;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 多数据源拦截器自动配置.
 *
 * @author guer
 */
@Slf4j
@ConditionalOnProperty(prefix = MultiDataSourceProperties.PREFIX, name = "enabled", havingValue = "true")
public class MultiDataSourceInterceptorAutoConfigure {

	/**
	 * MyBatisPlus拦截器.
	 */
	private final MybatisPlusInterceptor mybatisPlusInterceptor;

	/**
	 * 多数据源配置.
	 */
	private final MultiDataSourceProperties multiDataSourceProperties;

	/**
	 * 创建多数据源拦截器自动配置.
	 *
	 * @param mybatisPlusInterceptor    MyBatisPlus拦截器
	 * @param multiDataSourceProperties 多数据源配置
	 */
	public MultiDataSourceInterceptorAutoConfigure(MybatisPlusInterceptor mybatisPlusInterceptor, MultiDataSourceProperties multiDataSourceProperties) {
		this.mybatisPlusInterceptor = mybatisPlusInterceptor;
		this.multiDataSourceProperties = multiDataSourceProperties;
	}

	/**
	 * 创建多数据源拦截器.
	 *
	 * @param dataSourceContextHolder 数据源上下文保持
	 * @return 多数据源拦截器
	 */
	@Bean
	public MultiDataSourceInterceptor multiDataSourceInterceptor(DataSourceContextHolder dataSourceContextHolder) {
		List<PointcutProperties> pointcuts = CollectionUtil.toList(multiDataSourceProperties.getPointcuts(), Function.identity());
		MultiDataSourceInterceptor interceptor = new MultiDataSourceInterceptor(dataSourceContextHolder, pointcuts);
		List<InnerInterceptor> newList = new ArrayList<>();
		newList.add(interceptor);
		newList.addAll(mybatisPlusInterceptor.getInterceptors());
		mybatisPlusInterceptor.setInterceptors(newList);

		return interceptor;
	}
}
