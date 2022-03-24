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

package net.guerlab.cloud.web.webflux.request;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;
import net.guerlab.cloud.web.core.request.RequestContextHolder;
import net.guerlab.cloud.web.webflux.utils.RequestUtils;

/**
 * webflux请求上下文保持.
 *
 * @author guer
 */
@Slf4j
public class WebfluxRequestContextHolder implements RequestContextHolder {

	/**
	 * 获取ServerHttpRequest.
	 *
	 * @return ServerHttpRequest
	 */
	public static ServerHttpRequest getRequest() {
		ContextAttributes contextAttributes = ContextAttributesHolder.get();
		ServerHttpRequest request = (ServerHttpRequest) contextAttributes.get(ContextAttributes.REQUEST_KEY);

		if (request == null) {
			throw new NullPointerException("ServerHttpRequest is null");
		}

		return request;
	}

	@Nullable
	@Override
	public String getRequestMethod() {
		return getRequest().getMethodValue();
	}

	@Nullable
	@Override
	public String getRequestPath() {
		return RequestUtils.parseRequestUri(getRequest());
	}
}
