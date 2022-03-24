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

package net.guerlab.cloud.api.headers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求信息清理拦截器.
 *
 * @author guer
 */
@Slf4j
public class HeadersContextCleanHandlerInterceptor implements HandlerInterceptor, Ordered {

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) {
		HeadersContextHandler.clean();
		log.debug("invoke HeadersContextHandler.clean()");
	}

}
