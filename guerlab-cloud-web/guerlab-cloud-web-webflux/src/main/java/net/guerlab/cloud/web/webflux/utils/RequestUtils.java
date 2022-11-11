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

package net.guerlab.cloud.web.webflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 请求工具类.
 *
 * @author guer
 */
@Slf4j
public final class RequestUtils {

	private RequestUtils() {

	}

	/**
	 * 解析请求路径.
	 *
	 * @param request 请求对象
	 * @return 请求路径
	 */
	public static String parseRequestUri(ServerHttpRequest request) {
		return parseRequestUri0(request.getPath());
	}

	/**
	 * 解析请求路径.
	 *
	 * @param request 请求对象
	 * @return 请求路径
	 */
	public static String parseRequestUri(ServerRequest request) {
		return parseRequestUri0(request.requestPath());
	}

	private static String parseRequestUri0(RequestPath requestPath) {
		String contextPath = StringUtils.trimToNull(requestPath.contextPath().value());
		String requestUri = requestPath.value();

		if (contextPath != null) {
			String newRequestUri = requestUri.replaceFirst(contextPath, "");
			log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
			requestUri = newRequestUri;
		}

		return requestUri;
	}
}
