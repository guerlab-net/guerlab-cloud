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

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.core.result.Fail;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * 阻塞请求处理.
 *
 * @author guer
 */
public class CustomerBlockRequestHandler implements BlockRequestHandler {

	private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

	private final StackTracesHandler stackTracesHandler;

	/**
	 * 创建阻塞请求处理.
	 *
	 * @param stackTracesHandler 堆栈处理
	 */
	public CustomerBlockRequestHandler(StackTracesHandler stackTracesHandler) {
		this.stackTracesHandler = stackTracesHandler;
	}

	@Override
	public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
		// JSON result by default.
		return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(fromValue(buildErrorResult(ex)));
	}

	private Fail<Void> buildErrorResult(Throwable ex) {
		Fail<Void> result = new Fail<>();
		result.setErrorCode(HttpStatus.TOO_MANY_REQUESTS.value());
		result.setMessage(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName());

		stackTracesHandler.setStackTrace(result, ex);

		return result;
	}
}
