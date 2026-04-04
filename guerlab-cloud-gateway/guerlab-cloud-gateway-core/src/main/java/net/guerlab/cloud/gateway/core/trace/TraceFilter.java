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
import org.slf4j.MDC;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.core.Constants;
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
		ServerHttpRequest request = exchange.getRequest();
		String upstreamTraceId = request.getHeaders().getFirst(properties.getUpstreamHeaderKey());
		if (upstreamTraceId == null) {
			upstreamTraceId = UUID.randomUUID().toString();
		}

		ServerHttpRequest newRequest = request.mutate().header(Constants.REQUEST_TRACE_ID_HEADER, upstreamTraceId)
				.build();
		ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

		newExchange.getAttributes().put(GatewayConstants.TRACE_ID_KEY, upstreamTraceId);

		log.debug("set traceId: {}", upstreamTraceId);

		MDC.put(Constants.MDC_TRACE_ID_KEY, upstreamTraceId);

		try {
			return chain.filter(newExchange).doFinally(signal -> MDC.remove(Constants.MDC_TRACE_ID_KEY));
		}
		catch (Exception e) {
			MDC.remove(Constants.MDC_TRACE_ID_KEY);
			throw e;
		}
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}

