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

package net.guerlab.cloud.web.webmvc.request;

import jakarta.annotation.Nullable;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * webmvc请求上下文保持.
 *
 * @author guer
 */
@Slf4j
public class WebmvcRequestContextHolder implements net.guerlab.cloud.web.core.request.RequestContextHolder {

	@Nullable
	private static ServletRequestAttributes getServletRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}

	@Nullable
	private static HttpServletRequest getRequest() {
		ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
		return servletRequestAttributes != null ? servletRequestAttributes.getRequest() : null;
	}

	private static String parseRequestUri(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		// 处理因为forward导致的requestUri丢失的问题
		Object forwardRequestUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
		String requestUri = forwardRequestUri == null ? request.getRequestURI() : String.valueOf(forwardRequestUri);

		if (contextPath != null) {
			String newRequestUri = requestUri.replaceFirst(contextPath, "");
			log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
			requestUri = newRequestUri;
		}

		return requestUri;
	}

	@Nullable
	@Override
	public String getRequestMethod() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getMethod() : null;
	}

	@Nullable
	@Override
	public String getRequestPath() {
		HttpServletRequest request = getRequest();
		return request != null ? parseRequestUri(request) : null;
	}

	@Nullable
	@Override
	public Integer getResponseStatusCode() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return null;
		}
		Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (errorStatusCode instanceof Integer code) {
			return code;
		}
		return null;
	}
}
