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

package net.guerlab.cloud.server.mybatis.jdbc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.CollectionUtils;

import net.guerlab.commons.collection.CollectionUtil;

/**
 * 多数据源切面自动配置.
 *
 * @author guer
 */
@Slf4j
@ConditionalOnProperty(prefix = MultiDataSourceProperties.PREFIX, name = "enabled", havingValue = "true")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MultiDataSourceAdvisorAutoConfigure {

	/**
	 * 多数据源配置.
	 */
	private final MultiDataSourceProperties multiDataSourceProperties;

	public MultiDataSourceAdvisorAutoConfigure(MultiDataSourceProperties multiDataSourceProperties) {
		this.multiDataSourceProperties = multiDataSourceProperties;
	}

	/**
	 * 创建动态数据源bean构造处理过程.
	 *
	 * @param dataSourceContextHolder 数据源上下文保持
	 * @return 动态数据源bean构造处理过程
	 */
	@Bean
	public MultiDataSourceBeanPostProcessor multiDataSourceBeanPostProcessor(DataSourceContextHolder dataSourceContextHolder) {
		MultiDataSourceAdvisorProperties properties = multiDataSourceProperties.getAdvisors();

		List<String> basePackages = getBasePackages(properties);

		List<PointcutProperties> advisorPropertiesList = properties.getPointcuts();
		if (CollectionUtils.isEmpty(advisorPropertiesList)) {
			throw new IllegalArgumentException("multi dataSource advisor configure cannot be empty[" + MultiDataSourceProperties.PREFIX + ".advisors]");
		}

		List<Advisor> advisors = CollectionUtil.toList(advisorPropertiesList, p -> createAdvisor(dataSourceContextHolder, p));

		return new MultiDataSourceBeanPostProcessor(basePackages, advisors);
	}

	/**
	 * 获取基准包路径列表.
	 *
	 * @param properties 切面配置
	 * @return 包路径列表
	 */
	private List<String> getBasePackages(MultiDataSourceAdvisorProperties properties) {
		List<String> basePackages = CollectionUtil.toDistinctList(properties.getBasePackages(), StringUtils::trimToNull);
		if (basePackages.isEmpty()) {
			Class<?> mainClass = deduceMainApplicationClass();
			if (mainClass != null) {
				basePackages.add(mainClass.getPackageName());
			}
		}
		log.debug("basePackage: {}", basePackages);
		return basePackages;
	}

	@Nullable
	private Class<?> deduceMainApplicationClass() {
		return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
				.walk(this::findMainClass)
				.orElse(null);
	}

	private Optional<Class<?>> findMainClass(Stream<StackWalker.StackFrame> stack) {
		return stack.filter((frame) -> Objects.equals(frame.getMethodName(), "main"))
				.findFirst()
				.map(StackWalker.StackFrame::getDeclaringClass);
	}

	/**
	 * 创建切面.
	 *
	 * @param dataSourceContextHolder 数据源上下文保持
	 * @param properties              切面配置
	 * @return 切面
	 */
	private Advisor createAdvisor(DataSourceContextHolder dataSourceContextHolder, PointcutProperties properties) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(properties.getPointcut());

		MultiDataSourceAdvice advice = new MultiDataSourceAdvice(dataSourceContextHolder, properties.getDataSource());

		Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

		log.debug("create dynamic dataSource advisor: {}", advisor);

		return advisor;
	}

	/**
	 * 创建多数据源监控端点.
	 *
	 * @param multiDataSourceBeanPostProcessor 动态数据源bean构造处理过程
	 * @return 多数据源监控端点
	 */
	@Bean
	public MultiDataSourceAdvisorEndpoint multiDataSourceAdvisorEndpoint(MultiDataSourceBeanPostProcessor multiDataSourceBeanPostProcessor) {
		return new MultiDataSourceAdvisorEndpoint(multiDataSourceBeanPostProcessor);
	}
}
