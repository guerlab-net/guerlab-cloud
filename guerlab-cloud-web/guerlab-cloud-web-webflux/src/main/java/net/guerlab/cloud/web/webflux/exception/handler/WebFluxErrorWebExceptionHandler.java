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

package net.guerlab.cloud.web.webflux.exception.handler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.cloud.web.webflux.utils.RequestUtils;

/**
 * 异常处理.
 *
 * @author guer
 */
@Slf4j
public class WebFluxErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

	private final GlobalExceptionHandler globalExceptionHandler;

	/**
	 * 创建异常处理.
	 *
	 * @param errorAttributes
	 *         ErrorAttributes
	 * @param resources
	 *         WebProperties.Resources
	 * @param errorProperties
	 *         ErrorProperties
	 * @param applicationContext
	 *         ApplicationContext
	 * @param globalExceptionHandler
	 *         异常统一处理配置
	 */
	public WebFluxErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
			ErrorProperties errorProperties, ApplicationContext applicationContext,
			GlobalExceptionHandler globalExceptionHandler) {
		super(errorAttributes, resources, errorProperties, applicationContext);
		this.globalExceptionHandler = globalExceptionHandler;
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	@Override
	protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Throwable error = getError(request);
		globalExceptionHandler.getGlobalExceptionLogger()
				.debug(error, request.methodName(), RequestUtils.parseRequestUri(request));

		Fail<?> fail = globalExceptionHandler.build(error);

		return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(fail));
	}
}
