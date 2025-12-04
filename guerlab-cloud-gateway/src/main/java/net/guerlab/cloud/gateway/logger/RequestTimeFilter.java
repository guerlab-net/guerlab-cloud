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

package net.guerlab.cloud.gateway.logger;

import java.net.URI;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;

/**
 * 请求时间记录.
 *
 * @author guer
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestTimeFilter implements GlobalFilter {

	private static final String REQUEST_TIME = RequestTimeFilter.class.getName() + ".requestTimeStartWith";

	private final LoggerProperties properties;

	/**
	 * 创建实例.
	 *
	 * @param properties 日志配置
	 */
	public RequestTimeFilter(LoggerProperties properties) {
		this.properties = properties;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (!properties.isEnableRecordRequestTime()) {
			return chain.filter(exchange);
		}

		exchange.getAttributes().put(REQUEST_TIME, System.currentTimeMillis());
		return chain.filter(exchange).then(Mono.fromCallable(() -> {
			Long startTime = exchange.getAttribute(REQUEST_TIME);
			if (startTime == null) {
				return null;
			}

			String url = getRequestUrl(exchange);
			HttpMethod method = exchange.getRequest().getMethod();
			long usedTime = System.currentTimeMillis() - startTime;
			log.debug("request [{} {}] used: {} milliseconds", method, url, usedTime);
			return null;
		}));
	}

	/**
	 * 获取请求URL.
	 *
	 * @param exchange ServerWebExchange
	 * @return 请求URL
	 */
	private String getRequestUrl(ServerWebExchange exchange) {
		Set<URI> originalRequestUrls = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
		if (originalRequestUrls == null) {
			return exchange.getRequest().getURI().toString();
		}

		return originalRequestUrls.stream().findFirst().orElse(exchange.getRequest().getURI()).toString();
	}
}

