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

package net.guerlab.cloud.web.webmvc.interceptor;

import java.util.Enumeration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;
import net.guerlab.cloud.core.Constants;

/**
 * 透传header处理.
 *
 * @author guer
 */
@Slf4j
public class TransferHeaderInterceptor implements HandlerInterceptor {

	@Override
	public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Enumeration<String> headers = request.getHeaderNames();

		ContextAttributes contextAttributes = ContextAttributesHolder.get();

		String header;
		while (headers.hasMoreElements()) {
			// https://httpwg.org/specs/rfc7540.html#HttpHeaders 中要求请求头必须小写
			header = headers.nextElement().toLowerCase();
			if (!header.startsWith(Constants.ALLOW_TRANSFER_HEADER_PREFIX)) {
				log.debug("ignore transferHeader: {}", header);
				continue;
			}
			String value = request.getHeader(header);
			log.debug("transferHeader: {} = {}", header, value);

			contextAttributes.put(header, value);
		}
		return true;
	}
}
