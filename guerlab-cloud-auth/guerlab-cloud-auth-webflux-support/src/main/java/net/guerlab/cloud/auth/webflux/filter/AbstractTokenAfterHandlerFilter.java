/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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
 * 抽象token后置处理.
 *
 * @param <A> 授权配置类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractTokenAfterHandlerFilter<A extends AuthWebProperties>
		extends AbstractTokenHandlerFilter<A> {

	/**
	 * 初始化token后置处理.
	 *
	 * @param responseAdvisorProperties    http响应数据处理配置参数
	 * @param requestMappingHandlerMapping requestMappingHandlerMapping
	 * @param authProperties               认证配置
	 */
	protected AbstractTokenAfterHandlerFilter(ResponseAdvisorProperties responseAdvisorProperties,
			RequestMappingHandlerMapping requestMappingHandlerMapping, A authProperties) {
		super(responseAdvisorProperties, requestMappingHandlerMapping, authProperties);
	}

	@Override
	protected void preHandleWithToken(ServerHttpRequest request, HandlerMethod handlerMethod, String token) {

	}
}
