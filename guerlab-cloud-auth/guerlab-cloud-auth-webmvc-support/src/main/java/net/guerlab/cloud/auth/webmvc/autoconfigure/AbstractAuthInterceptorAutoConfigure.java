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

package net.guerlab.cloud.auth.webmvc.autoconfigure;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.guerlab.cloud.auth.web.properties.AuthWebProperties;
import net.guerlab.cloud.auth.webmvc.interceptor.AbstractHandlerInterceptor;
import net.guerlab.cloud.auth.webmvc.interceptor.AbstractTokenHandlerInterceptor;

/**
 * 抽象鉴权拦截器配置.
 *
 * @author guer
 */
@Slf4j
public abstract class AbstractAuthInterceptorAutoConfigure<A extends AuthWebProperties> implements WebMvcConfigurer {

	private final A properties;

	private Collection<? extends AbstractHandlerInterceptor> tokenHandlerInterceptors;

	/**
	 * 初始化鉴权拦截器配置.
	 *
	 * @param properties 认证配置
	 */
	protected AbstractAuthInterceptorAutoConfigure(A properties) {
		this.properties = properties;
	}

	@Override
	public final void addInterceptors(InterceptorRegistry registry) {
		addTokenHandlerInterceptors(registry);
		addInterceptorsInternal(registry);
	}

	private void addTokenHandlerInterceptors(InterceptorRegistry registry) {
		if (tokenHandlerInterceptors == null || tokenHandlerInterceptors.isEmpty()) {
			log.debug("tokenHandlerInterceptors is empty");
			return;
		}

		for (AbstractHandlerInterceptor interceptor : tokenHandlerInterceptors) {
			log.debug("register token interceptor[{}]", interceptor);
			setPathPatterns(registry.addInterceptor(interceptor).order(interceptor.getOrder()));
		}
	}

	/**
	 * 添加其他拦截器处理.
	 *
	 * @param registry InterceptorRegistry实例
	 */
	@SuppressWarnings({"EmptyMethod", "unused"})
	protected void addInterceptorsInternal(InterceptorRegistry registry) {
		/* 默认空实现 */
	}

	/**
	 * 设置路径信息.
	 *
	 * @param interceptor 拦截器
	 */
	protected final void setPathPatterns(InterceptorRegistration interceptor) {
		List<String> includePatterns = properties.getIncludePatterns();
		List<String> excludePatterns = properties.getExcludePatterns();

		interceptor.addPathPatterns(includePatterns).excludePathPatterns(excludePatterns);
	}

	/**
	 * 设置token处理拦截器列表.
	 *
	 * @param tokenHandlerInterceptors token处理拦截器列表
	 */
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired(required = false)
	public void setTokenHandlerInterceptors(
			Collection<? extends AbstractTokenHandlerInterceptor<A>> tokenHandlerInterceptors) {
		log.debug("input interceptors: {}", tokenHandlerInterceptors);
		this.tokenHandlerInterceptors = tokenHandlerInterceptors;
	}
}
