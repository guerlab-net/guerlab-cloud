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

package net.guerlab.cloud.sentinel.webflux.autoconfigure;

import java.util.List;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.function.Supplier;
import reactor.core.publisher.Mono;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

/**
 * Sentinel同步异常处理.
 *
 * @author guer
 */
public class SentinelBlockExceptionHandler implements WebExceptionHandler {

	private final BlockRequestHandler blockRequestHandler;

	private final Supplier<ServerResponse.Context> contextSupplier;

	/**
	 * 初始化Sentinel同步异常处理.
	 *
	 * @param viewResolvers         ViewResolver列表
	 * @param serverCodecConfigurer serverCodecConfigurer
	 * @param blockRequestHandler   blockRequestHandler
	 */
	public SentinelBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer,
			BlockRequestHandler blockRequestHandler) {
		this.blockRequestHandler = blockRequestHandler;
		contextSupplier = () -> new ServerResponse.Context() {

			@Override
			public List<HttpMessageWriter<?>> messageWriters() {
				return serverCodecConfigurer.getWriters();
			}

			@Override
			public List<ViewResolver> viewResolvers() {
				return viewResolvers;
			}
		};
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		// This exception handler only handles rejection by Sentinel.
		if (!BlockException.isBlockException(ex)) {
			return Mono.error(ex);
		}
		return blockRequestHandler.handleRequest(exchange, ex)
				.flatMap(response -> response.writeTo(exchange, contextSupplier.get()));
	}
}
