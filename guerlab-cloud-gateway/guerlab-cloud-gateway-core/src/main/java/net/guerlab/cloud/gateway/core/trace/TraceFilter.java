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

package net.guerlab.cloud.gateway.core.trace;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.gateway.core.GatewayConstants;

/**
 * 链路追踪拦截器.
 *
 * @author guer
 */
@Slf4j
public class TraceFilter implements GlobalFilter, Ordered {

	private final TraceProperties properties;

	/**
	 * 创建实例.
	 *
	 * @param properties 日志配置
	 */
	public TraceFilter(TraceProperties properties) {
		this.properties = properties;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String upstreamTraceId = exchange.getRequest().getHeaders().getFirst(properties.getUpstreamHeaderKey());
		if (upstreamTraceId == null) {
			upstreamTraceId = UUID.randomUUID().toString();
		}

		exchange.getAttributes().put(GatewayConstants.TRACE_ID_KEY, upstreamTraceId);
		log.debug("set tractId: {}", upstreamTraceId);
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}

