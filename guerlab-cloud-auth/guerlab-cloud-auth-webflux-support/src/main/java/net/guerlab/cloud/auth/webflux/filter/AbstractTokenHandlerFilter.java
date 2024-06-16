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

package net.guerlab.cloud.auth.webflux.filter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;

import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import net.guerlab.cloud.auth.annotation.AuthType;
import net.guerlab.cloud.auth.annotation.IgnoreLogin;
import net.guerlab.cloud.auth.context.AbstractContextHandler;
import net.guerlab.cloud.auth.web.properties.AuthWebProperties;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象拦截器处理.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractTokenHandlerFilter<A extends AuthWebProperties> implements WebFilter, Ordered {

	/**
	 * 默认排序.
	 */
	public static final int DEFAULT_ORDER = 0;

	private static final String[] IGNORE_METHODS = new String[] {"OPTIONS", "TRACE"};

	private static final String REAL_REQUEST_PATH = "realRequestPath";

	/**
	 * http响应数据处理配置参数.
	 */
	protected final ResponseAdvisorProperties responseAdvisorProperties;

	/**
	 * requestMappingHandlerMapping.
	 */
	protected final RequestMappingHandlerMapping requestMappingHandlerMapping;

	/**
	 * 授权配置.
	 */
	@Getter
	protected final A authProperties;

	/**
	 * 初始化Token处理过滤器.
	 *
	 * @param responseAdvisorProperties    http响应数据处理配置参数
	 * @param requestMappingHandlerMapping requestMappingHandlerMapping
	 * @param authProperties               认证配置
	 */
	protected AbstractTokenHandlerFilter(ResponseAdvisorProperties responseAdvisorProperties,
			RequestMappingHandlerMapping requestMappingHandlerMapping, A authProperties) {
		this.responseAdvisorProperties = responseAdvisorProperties;
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
		this.authProperties = authProperties;
	}

	/**
	 * 获取注解.
	 *
	 * @param handlerMethod   处理方法
	 * @param annotationClass 注解类
	 * @param <A>             注解类
	 * @return 注解对象
	 */
	@SuppressWarnings("SameParameterValue")
	@Nullable
	protected static <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationClass) {
		A annotation = handlerMethod.getMethodAnnotation(annotationClass);
		if (annotation == null) {
			Class<?> clazz = handlerMethod.getBeanType();
			annotation = clazz.getAnnotation(annotationClass);

			if (annotation == null) {
				annotation = clazz.getPackage().getAnnotation(annotationClass);
			}
		}
		return annotation;
	}

	private static boolean isIgnoreMethod(ServerHttpRequest request) {
		String requestMethod = request.getMethod().name();

		return Arrays.asList(IGNORE_METHODS).contains(requestMethod);
	}

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}

	@Override
	public final Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return requestMappingHandlerMapping.getHandlerInternal(exchange)
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
				.flatMap(handlerMethod -> {
					preHandle(exchange.getRequest(), handlerMethod, exchange);
					return chain.filter(exchange).then(Mono.empty());
				});
	}

	/**
	 * 请求前置处理.
	 *
	 * @param request       请求
	 * @param handlerMethod 处理方法
	 * @param exchange      交换器
	 */
	protected final void preHandle(ServerHttpRequest request, HandlerMethod handlerMethod, ServerWebExchange exchange) {
		String requestUri = parseRequestUri(request);

		if (isIgnoreMethod(request)) {
			log.debug("is ignore method[interceptor = {}, requestMethod = [{}]]", this, request.getMethod().name());
			return;
		}
		else if (uriNotMatch(requestUri)) {
			log.debug("not match uri request[interceptor = {}, requestUri = [{}]]", this, requestUri);
			return;
		}

		log.debug("intercept request[interceptor = {}, request = [{} {}]]", this, request.getMethod()
				.name(), requestUri);

		boolean needLogin = getAnnotation(handlerMethod, IgnoreLogin.class) == null;
		AuthType authType = getAnnotation(handlerMethod, AuthType.class);
		List<Class<?>> targetAuthTypes = Collections.emptyList();
		if (authType != null) {
			targetAuthTypes = Arrays.asList(authType.value());
		}

		log.debug("needLoginCheck[handler = {}, needLogin = {}]", handlerMethod, needLogin);

		String token = getToken(request, exchange);
		if (token != null) {
			try {
				preHandleWithToken(request, handlerMethod, token, targetAuthTypes);
				AbstractContextHandler.setToken(token);
			}
			catch (ApplicationException exception) {
				if (needLogin) {
					throw exception;
				}
			}
		}
		else if (needLogin) {
			preHandleWithoutToken();
		}
	}

	/**
	 * 获取token.
	 *
	 * @param request http请求对象
	 * @return token
	 */
	@Nullable
	private String getToken(ServerHttpRequest request, ServerWebExchange exchange) {
		ContextAttributes contextAttributes = (ContextAttributes) exchange.getAttributes().get(ContextAttributes.KEY);
		log.debug("contextAttributes: {}", contextAttributes);
		AbstractContextHandler.setContextAttributes(contextAttributes);
		String token = AbstractContextHandler.getToken();

		if (token != null) {
			log.debug("get token by contextHandler[token = {}]", token);
			return token;
		}

		token = StringUtils.trimToNull(request.getHeaders().getFirst(Constants.TOKEN));
		if (token != null) {
			log.debug("get token by header[token = {}]", token);
			return token;
		}

		HttpCookie httpCookie = request.getCookies().getFirst(Constants.TOKEN);
		if (httpCookie != null) {
			token = StringUtils.trimToNull(httpCookie.getValue());
		}

		if (token != null) {
			log.debug("get token by cookie[token = {}]", token);
			return token;
		}

		token = StringUtils.trimToNull(request.getQueryParams().getFirst(Constants.TOKEN));

		if (token != null) {
			log.debug("get token by parameter[token = {}]", token);
		}
		else {
			log.debug("get token fail");
		}

		return token;
	}

	/**
	 * 获取令牌成功前置处理.
	 *
	 * @param request         请求
	 * @param handlerMethod   处理方法
	 * @param token           令牌
	 * @param targetAuthTypes 目标认证类型列表
	 */
	protected abstract void preHandleWithToken(ServerHttpRequest request, HandlerMethod handlerMethod, String token, List<Class<?>> targetAuthTypes);

	/**
	 * 获取令牌失败前置处理.
	 */
	protected abstract void preHandleWithoutToken();

	private boolean uriNotMatch(String requestUri) {
		boolean responseAdvisorExcluded = responseAdvisorProperties.getExcluded().stream()
				.anyMatch(requestUri::startsWith);

		if (responseAdvisorExcluded) {
			log.debug("responseAdvisorExcluded, {}", requestUri);
			return true;
		}

		boolean uriNotMatch = !authProperties.match(requestUri);
		log.debug("uriNotMatch, {}, {}", uriNotMatch, requestUri);
		return uriNotMatch;
	}

	private String parseRequestUri(ServerHttpRequest request) {
		String contextPath = StringUtils.trimToNull(request.getPath().contextPath().value());
		String requestUri = request.getPath().value();

		if (contextPath != null) {
			String newRequestUri = requestUri.replaceFirst(contextPath, "");
			log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
			requestUri = newRequestUri;
		}

		return requestUri;
	}

}
