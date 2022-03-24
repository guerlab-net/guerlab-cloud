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

package net.guerlab.cloud.web.webflux.support;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.commons.ip.IpUtils;
import net.guerlab.cloud.web.core.annotation.RequestIp;

/**
 * 请求IP参数处理.
 *
 * @author guer
 */
@Slf4j
public class WebFluxRequestIpHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(RequestIp.class) != null;
	}

	@Override
	public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
			ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		log.debug("serverHttpRequest: {}", request);
		String ip = IpUtils.getIp(request);
		log.debug("get ip in serverHttpRequest: {}", ip);
		return Mono.just(ip);
	}
}
