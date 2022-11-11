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

package net.guerlab.cloud.web.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.core.Ordered;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.webflux.filter.ContextAttributesServerWebExchangeDecoratorFilter;

/**
 * 当前操作者信息处理请求拦截器.
 *
 * @author guer
 */
@Slf4j
public class CurrentOperatorFilter implements WebFilter, Ordered {

	/**
	 * 默认排序.
	 */
	public static final int DEFAULT_ORDER = ContextAttributesServerWebExchangeDecoratorFilter.DEFAULT_ORDER + 10;

	/**
	 * requestMappingHandlerMapping.
	 */
	protected final RequestMappingHandlerMapping requestMappingHandlerMapping;

	/**
	 * 初始化当前操作者信息处理请求拦截器.
	 *
	 * @param requestMappingHandlerMapping requestMappingHandlerMapping
	 */
	public CurrentOperatorFilter(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String currentOperator = exchange.getRequest().getHeaders().getFirst(Constants.CURRENT_OPERATOR_HEADER);
		log.debug("currentOperator: {}", currentOperator);
		return requestMappingHandlerMapping.getHandlerInternal(exchange)
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
				.flatMap(handlerMethod -> {
					if (currentOperator != null) {
						ContextAttributes contextAttributes = (ContextAttributes) exchange.getAttributes()
								.get(ContextAttributes.KEY);
						contextAttributes.put(Constants.CURRENT_OPERATOR, currentOperator);
					}
					return chain.filter(exchange).then(Mono.empty());
				});
	}
}
