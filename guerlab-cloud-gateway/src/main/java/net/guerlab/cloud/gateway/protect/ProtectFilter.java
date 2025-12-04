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

package net.guerlab.cloud.gateway.protect;

import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.gateway.UrlDefinition;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 接口保护请求过滤器.
 *
 * @author guer
 */
@Slf4j
public class ProtectFilter implements GatewayFilter, GlobalFilter, Ordered {

	private final ProtectProperties properties;

	/**
	 * 创建实例.
	 *
	 * @param properties 接口保护配置
	 */
	public ProtectFilter(ProtectProperties properties) {
		this.properties = properties;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (!properties.isEnable()) {
			return chain.filter(exchange);
		}

		List<UrlDefinition> urls = properties.getUrls();

		if (CollectionUtil.isEmpty(urls)) {
			return chain.filter(exchange);
		}

		boolean release = false;
		String releaseName = StringUtils.trimToNull(properties.getReleaseHeaderName());
		String releaseValue = StringUtils.trimToNull(properties.getReleaseHeaderValue());
		String requestValue;
		if (releaseName != null && releaseValue != null) {
			requestValue = exchange.getRequest().getHeaders().getFirst(releaseName);
			release = requestValue != null && Objects.equals(requestValue, releaseValue);
		}

		if (release) {
			return chain.filter(exchange);
		}

		String requestMethod = exchange.getRequest().getMethod().name();
		String requestPath = exchange.getRequest().getURI().getPath();

		for (UrlDefinition url : urls) {
			if (url.match(requestMethod, requestPath)) {
				log.debug("intercept protect uri, requestInfo=[{} {}], protectUri=[{}]", requestMethod, requestPath,
						url);
				return createNotFoundError();
			}
		}

		return chain.filter(exchange);
	}

	private <R> Mono<R> createNotFoundError() {
		return Mono.defer(() -> {
			Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching handler");
			return Mono.error(ex);
		});
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
