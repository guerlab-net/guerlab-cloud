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

package net.guerlab.cloud.gateway.core.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import net.guerlab.cloud.core.result.ApplicationStackTrace;
import net.guerlab.cloud.core.result.RemoteException;
import net.guerlab.cloud.core.util.SpringUtils;

/**
 * 自定义错误处理.
 *
 * @author guer
 */
@Slf4j
class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

	private static final String FAVICON_ICO_PATH = "/favicon.ico";

	private final ExceptionMessageHandler defaultHandler = new DefaultExceptionMessageHandler();

	private final List<ExceptionMessageHandler> handlers;

	private final ObjectProvider<ErrorAttributesWrapper> wrappersProvider;

	CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resourceProperties,
			ErrorProperties errorProperties, ApplicationContext applicationContext,
			List<ExceptionMessageHandler> exceptionMessageHandlers,
			ObjectProvider<ErrorAttributesWrapper> wrappersProvider) {
		super(errorAttributes, resourceProperties, errorProperties, applicationContext);
		this.handlers = exceptionMessageHandlers;
		this.wrappersProvider = wrappersProvider;
	}

	@Override
	protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Throwable error = super.getError(request);

		if (!FAVICON_ICO_PATH.equals(request.path())) {
			log.debug(error.getLocalizedMessage(), error);
		}

		ExceptionMessageHandler handler = handlers.stream().filter(provider -> provider.accept(error)).sorted()
				.findFirst().orElse(defaultHandler);

		Map<String, Object> errorAttributes = new HashMap<>(4);
		errorAttributes.put("status", false);
		errorAttributes.put("message", handler.getMessage(error));
		errorAttributes.put("errorCode", handler.getErrorCode(error));
		errorAttributes.put("stackTraces", getStackTraces(error));

		for (ErrorAttributesWrapper wrapper : wrappersProvider) {
			errorAttributes = wrapper.wrapper(request, errorAttributes);
		}

		return errorAttributes;
	}

	private List<ApplicationStackTrace> getStackTraces(Throwable throwable) {
		List<ApplicationStackTrace> stackTraces = new ArrayList<>();
		setSubStackTrace(stackTraces, throwable);
		return stackTraces;
	}

	private void setSubStackTrace(List<ApplicationStackTrace> stackTraces, @Nullable Throwable throwable) {
		if (throwable == null) {
			return;
		}
		setSubStackTrace(stackTraces, throwable.getCause());

		if (throwable instanceof RemoteException remoteException) {
			stackTraces.add(remoteException.getApplicationStackTrace());
		}
		else {
			ApplicationStackTrace applicationStackTrace = new ApplicationStackTrace();
			applicationStackTrace.setApplicationName(SpringUtils.getApplicationName());
			applicationStackTrace.setStackTrace(
					Arrays.stream(throwable.getStackTrace()).map(this::buildStackTraceElementText).toList());

			stackTraces.add(applicationStackTrace);
		}
	}

	private String buildStackTraceElementText(StackTraceElement element) {
		return element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	@NonNull
	@Override
	protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		return super.renderErrorResponse(request);
	}

	@Override
	protected int getHttpStatus(Map<String, Object> errorAttributes) {
		return HttpStatus.OK.value();
	}
}
