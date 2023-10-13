/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.context.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;

/**
 * 上下文属性包装器处理.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public class ContextAttributesServerWebExchangeDecoratorFilter implements WebFilter, Ordered {

	/**
	 * 默认排序.
	 */
	public static final int DEFAULT_ORDER = -100;

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}

	@Override
	public final Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter(new ContextAttributesServerWebExchangeDecorator(exchange));
	}

	/**
	 * 上下文属性包装器.
	 *
	 * @author guer
	 */
	public static class ContextAttributesServerWebExchangeDecorator extends ServerWebExchangeDecorator {

		/**
		 * 创建上下文属性包装器.
		 *
		 * @param delegate 原始ServerWebExchange
		 */
		protected ContextAttributesServerWebExchangeDecorator(ServerWebExchange delegate) {
			super(delegate);
			ContextAttributes contextAttributes = new ContextAttributes();
			contextAttributes.put(ContextAttributes.REQUEST_KEY, delegate.getRequest());
			contextAttributes.put(ContextAttributes.RESPONSE_KEY, delegate.getResponse());

			delegate.getAttributes().put(ContextAttributes.KEY, contextAttributes);
			ContextAttributesHolder.set(contextAttributes);
		}
	}
}
