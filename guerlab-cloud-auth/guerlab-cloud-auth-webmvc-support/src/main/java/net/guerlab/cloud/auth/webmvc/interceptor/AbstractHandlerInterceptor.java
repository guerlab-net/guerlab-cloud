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

package net.guerlab.cloud.auth.webmvc.interceptor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import net.guerlab.cloud.auth.annotation.IgnoreLogin;
import net.guerlab.cloud.auth.context.AbstractContextHandler;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象拦截器处理.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractHandlerInterceptor implements HandlerInterceptor, Ordered {

	/**
	 * 默认排序.
	 */
	public static final int DEFAULT_ORDER = 0;

	private static final String[] METHODS = new String[] {"OPTIONS", "TRACE"};

	private static final String REAL_REQUEST_PATH = "realRequestPath";

	/**
	 * http响应数据处理配置参数.
	 */
	protected ResponseAdvisorProperties responseAdvisorProperties;

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

	private static boolean methodMatch(HttpServletRequest request) {
		String requestMethod = request.getMethod();

		return Arrays.asList(METHODS).contains(requestMethod);
	}

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}

	@Override
	public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		preHandle0(request, handler);
		return true;
	}

	private void preHandle0(HttpServletRequest request, Object handler) {
		if (methodMatch(request) || uriMatch(request) || !(handler instanceof HandlerMethod handlerMethod)) {
			return;
		}

		log.debug("intercept request[interceptor = {}, request = [{} {}]]", getClass(), request.getMethod(),
				parseRequestUri(request));

		boolean needLogin = getAnnotation(handlerMethod, IgnoreLogin.class) == null;

		log.debug("needLoginCheck[handler = {}, needLogin = {}]", handler, needLogin);

		String token = getToken(request);
		if (token != null) {
			try {
				preHandleWithToken(request, handlerMethod, token);
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
	private String getToken(HttpServletRequest request) {
		String token = AbstractContextHandler.getToken();

		if (token != null) {
			log.debug("get token by contextHandler[token = {}]", token);
			return token;
		}

		token = StringUtils.trimToNull(request.getHeader(Constants.TOKEN));
		if (token != null) {
			log.debug("get token by header[token = {}]", token);
			return token;
		}

		if (request.getCookies() != null) {
			Optional<Cookie> optional = Arrays.stream(request.getCookies())
					.filter(cookie -> Constants.TOKEN.equals(cookie.getName())).findFirst();

			if (optional.isPresent()) {
				token = StringUtils.trimToNull(optional.get().getValue());
			}
		}

		if (token != null) {
			log.debug("get token by cookie[token = {}]", token);
			return token;
		}

		token = StringUtils.trimToNull(request.getParameter(Constants.TOKEN));

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
	 * @param request       请求
	 * @param handlerMethod 处理方法
	 * @param token         令牌
	 */
	protected void preHandleWithToken(HttpServletRequest request, HandlerMethod handlerMethod, String token) {

	}

	/**
	 * 获取令牌失败前置处理.
	 */
	protected void preHandleWithoutToken() {
		log.debug("invoke preHandleWithoutToken()");
	}

	private boolean uriMatch(HttpServletRequest request) {
		String requestUri = parseRequestUri(request);
		return responseAdvisorProperties.getExcluded().stream().anyMatch(requestUri::startsWith);
	}

	private String parseRequestUri(HttpServletRequest request) {
		String realRequestPath = null;
		Object attribute = request.getAttribute(REAL_REQUEST_PATH);
		if (attribute instanceof String) {
			realRequestPath = (String) attribute;
		}
		if (realRequestPath != null) {
			return realRequestPath;
		}

		String contextPath = request.getContextPath();
		realRequestPath = request.getRequestURI();
		if (contextPath != null) {
			String newRequestUri = realRequestPath.replaceFirst(contextPath, "");
			log.debug("replace requestUri[form={}, to={}]", realRequestPath, newRequestUri);
			realRequestPath = newRequestUri;
		}

		request.setAttribute(REAL_REQUEST_PATH, realRequestPath);

		return realRequestPath;
	}

	/**
	 * 设置http响应数据处理配置参数.
	 *
	 * @param responseAdvisorProperties http响应数据处理配置参数
	 */
	@SuppressWarnings("SpringJavaAutowiredMembersInspection")
	@Autowired
	public void setResponseAdvisorProperties(ResponseAdvisorProperties responseAdvisorProperties) {
		this.responseAdvisorProperties = responseAdvisorProperties;
	}
}
