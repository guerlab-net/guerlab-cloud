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

package net.guerlab.cloud.web.webflux.response;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.core.result.Result;
import net.guerlab.cloud.core.result.Succeed;
import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;
import net.guerlab.cloud.web.webflux.utils.RequestUtils;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 响应内容结果包装处理.
 *
 * @author guer
 */
@Slf4j
public class ResponseBodyResultWrapperHandler extends ResponseBodyResultHandler {

	private static final MethodParameter METHOD_PARAMETER_WITH_MONO_RESULT;

	private static final MethodParameter METHOD_PARAMETER_WITH_MONO_STRING;

	static {
		try {
			METHOD_PARAMETER_WITH_MONO_RESULT = new MethodParameter(
					ResponseBodyResultWrapperHandler.class.getDeclaredMethod("methodForParamsWithResult"), -1);
			METHOD_PARAMETER_WITH_MONO_STRING = new MethodParameter(
					ResponseBodyResultWrapperHandler.class.getDeclaredMethod("methodForParamsWithString"), -1);
		}
		catch (NoSuchMethodException e) {
			throw new ApplicationException(e);
		}
	}

	private final ResponseBodyWrapperSupport support;

	/**
	 * 初始化响应内容结果包装处理.
	 *
	 * @param writers  HttpMessageWriter列表
	 * @param resolver RequestedContentTypeResolver
	 * @param support  响应对象包装支持
	 */
	public ResponseBodyResultWrapperHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver,
			ResponseBodyWrapperSupport support) {
		super(writers, resolver);
		this.support = support;
		setOrder(-10);
	}

	@SuppressWarnings("SameReturnValue")
	@Nullable
	private static Mono<Result<?>> methodForParamsWithResult() {
		return null;
	}

	@SuppressWarnings("SameReturnValue")
	@Nullable
	private static Mono<String> methodForParamsWithString() {
		return null;
	}

	private static Class<?> resolveReturnValueType(HandlerResult result) {
		Class<?> valueType = result.getReturnType().toClass();
		Object value = result.getReturnValue();
		if (valueType == Object.class && value != null) {
			valueType = value.getClass();
		}
		return valueType;
	}

	@Override
	public boolean supports(HandlerResult result) {
		Class<?> valueType = resolveReturnValueType(result);
		if (isIgnoreType(valueType)) {
			return false;
		}

		ReactiveAdapter adapter = getAdapter(result);
		if (adapter != null && !adapter.isNoValue() && isIgnoreType(result.getReturnType().getGeneric().toClass())) {
			return false;
		}

		return super.supports(result) && support.supports(result.getReturnTypeSource());
	}

	private boolean isIgnoreType(@Nullable Class<?> clazz) {
		return (clazz != null && (
				(HttpEntity.class.isAssignableFrom(clazz) && !RequestEntity.class.isAssignableFrom(clazz))
						|| HttpHeaders.class.isAssignableFrom(clazz)));
	}

	@Override
	public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
		Object body = result.getReturnValue();
		MethodParameter bodyTypeParameter = result.getReturnTypeSource();

		if (support.noConvertObject(bodyTypeParameter)) {
			log.debug("un wrapper with noConvertObject");
			return super.handleResult(exchange, result);
		}
		else if (matchExcluded(exchange, bodyTypeParameter.getMethod())) {
			log.debug("un wrapper with matchExcluded");
			return super.handleResult(exchange, result);
		}

		boolean returnTypeIsString = Objects.equals(Objects.requireNonNull(bodyTypeParameter.getMethod())
				.getReturnType(), String.class);

		exchange.getResponse().getHeaders().set(Constants.HTTP_HEADER_RESPONSE_WRAPPED, "true");

		if (returnTypeIsString) {
			if (body == null) {
				return writeBody(new Succeed<>(), METHOD_PARAMETER_WITH_MONO_RESULT, exchange);
			}
			else {
				return writeBody(body, METHOD_PARAMETER_WITH_MONO_RESULT, METHOD_PARAMETER_WITH_MONO_STRING, exchange);
			}
		}
		else {
			if (body == null) {
				log.debug("wrapper with null body");
				return writeBody(new Succeed<>(), METHOD_PARAMETER_WITH_MONO_RESULT, exchange);
			}
			else {
				Mono<?> mono = null;
				if (body instanceof Mono) {
					mono = (Mono<?>) body;
					log.debug("wrapper with Mono");
				}
				else if (body instanceof Flux<?> flux) {
					mono = flux.collectList();
					log.debug("wrapper with Flux");
				}

				if (mono != null) {
					body = mono.map(Succeed::new).defaultIfEmpty(new Succeed<>());
				}
				else {
					body = new Succeed<>(body);
				}

				return writeBody(body, METHOD_PARAMETER_WITH_MONO_RESULT, exchange);
			}
		}
	}

	private boolean matchExcluded(ServerWebExchange exchange, @Nullable Method method) {
		return support.matchExcluded(getRequestPath(exchange), method);
	}

	private String getRequestPath(ServerWebExchange exchange) {
		return RequestUtils.parseRequestUri(exchange.getRequest());
	}
}
