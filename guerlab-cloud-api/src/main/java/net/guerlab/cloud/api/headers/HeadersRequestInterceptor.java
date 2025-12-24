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

package net.guerlab.cloud.api.headers;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import net.guerlab.cloud.context.core.ContextAttributesHolder;
import net.guerlab.cloud.context.core.HeaderSafetyFilter;

/**
 * 请求头处理请求拦截器.
 *
 * @author guer
 */
@Slf4j
public class HeadersRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		ContextAttributesHolder.getAllTransfer().forEach((header, value) -> addHeader(header, value, requestTemplate));
		HeadersContextHandler.forEach((header, value) -> addHeader(header, value, requestTemplate));
	}

	private void addHeader(String header, String value, RequestTemplate requestTemplate) {
		if (HeaderSafetyFilter.accept(header, value)) {
			log.debug("add header: {} = {}", header, value);
			requestTemplate.header(header, value);
		}
	}
}
