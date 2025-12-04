/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.gateway.base;

import java.util.Arrays;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Cors处理拦截器.
 *
 * @author guer
 */
public class CorsFilter implements WebFilter {

	private static final long MAX_AGE = 18000L;

	private final CorsProperties properties;

	public CorsFilter(CorsProperties properties) {
		this.properties = properties;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		if (!CorsUtils.isCorsRequest(request)) {
			return chain.filter(exchange);
		}

		ServerHttpResponse response = exchange.getResponse();

		if (request.getMethod() != HttpMethod.OPTIONS) {
			return chain.filter(exchange);
		}

		HttpHeaders requestHeaders = request.getHeaders();

		HttpHeaders headers = response.getHeaders();
		headers.setAccessControlAllowOrigin(requestHeaders.getOrigin());
		headers.setAccessControlAllowHeaders(requestHeaders.getAccessControlRequestHeaders());
		headers.setAccessControlAllowCredentials(true);
		headers.setAccessControlAllowMethods(Arrays.asList(HttpMethod.values()));

		if (properties.getAccessControlExposeHeaders().isEmpty()) {
			headers.setAccessControlRequestHeaders(properties.getAccessControlExposeHeaders());
		}

		headers.setAccessControlMaxAge(MAX_AGE);
		response.setStatusCode(HttpStatus.OK);
		return Mono.empty();
	}

}
