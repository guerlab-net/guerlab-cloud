/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.auth.webflux.filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

import net.guerlab.cloud.auth.web.properties.AuthWebProperties;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;

/**
 * 抽象token处理.
 *
 * @param <A>
 *         授权配置类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractTokenBeforeHandlerFilter<A extends AuthWebProperties>
		extends AbstractTokenHandlerFilter<A> {

	/**
	 * 初始化token处理器.
	 *
	 * @param responseAdvisorProperties
	 *         http响应数据处理配置参数
	 * @param requestMappingHandlerMapping
	 *         requestMappingHandlerMapping
	 * @param authProperties
	 *         认证配置
	 */
	protected AbstractTokenBeforeHandlerFilter(ResponseAdvisorProperties responseAdvisorProperties,
			RequestMappingHandlerMapping requestMappingHandlerMapping, A authProperties) {
		super(responseAdvisorProperties, requestMappingHandlerMapping, authProperties);
	}

	@Override
	public int getOrder() {
		return DEFAULT_ORDER - 10;
	}

	@Override
	protected void preHandleWithToken(ServerHttpRequest request, HandlerMethod handlerMethod, String token) {
		boolean accept = accept(token, request);

		log.debug("token preHandler[instance = {}, accept = {}, token = {}]", this, accept, token);

		if (accept) {
			setTokenInfo(token);
		}
	}

	@Override
	protected void preHandleWithoutToken() {
		log.debug("invoke preHandleWithoutToken()");
	}

	/**
	 * 判断是否处理该token.
	 *
	 * @param token
	 *         token
	 * @param request
	 *         请求对象
	 * @return 是否处理该token
	 */
	protected abstract boolean accept(String token, ServerHttpRequest request);

	/**
	 * 设置Token信息.
	 *
	 * @param token
	 *         token
	 */
	protected abstract void setTokenInfo(String token);
}
