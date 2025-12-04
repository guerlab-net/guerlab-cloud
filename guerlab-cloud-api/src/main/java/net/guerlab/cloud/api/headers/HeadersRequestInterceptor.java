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

import java.util.function.Predicate;
import java.util.regex.Pattern;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import net.guerlab.cloud.auth.context.AbstractContextHandler;

/**
 * 请求头处理请求拦截器.
 *
 * @author guer
 */
@Slf4j
public class HeadersRequestInterceptor implements RequestInterceptor {

	private static final Pattern ASSIGNED_AND_NOT_ISO_CONTROL_PATTERN = Pattern
			.compile("[\\p{IsAssigned}&&[^\\p{IsControl}]]*");

	private static final Predicate<String> ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE = (s) -> ASSIGNED_AND_NOT_ISO_CONTROL_PATTERN.matcher(s)
			.matches();

	private static final Pattern HEADER_VALUE_PATTERN = Pattern.compile("[\\p{IsAssigned}&&[[^\\p{IsControl}]||\\t]]*");

	private static final Predicate<String> HEADER_VALUE_PREDICATE = (s) -> HEADER_VALUE_PATTERN.matcher(s).matches();

	@Override
	public void apply(RequestTemplate requestTemplate) {
		AbstractContextHandler.getAllTransfer().forEach((header, value) -> addHeader(header, value, requestTemplate));
		HeadersContextHandler.forEach((header, value) -> addHeader(header, value, requestTemplate));
	}

	private void addHeader(String header, String value, RequestTemplate requestTemplate) {
		if (!ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE.test(header)) {
			log.debug("header not match pattern: {}", header);
			return;
		}
		if (!HEADER_VALUE_PREDICATE.test(value)) {
			log.debug("header value not match pattern: {} = {}", header, value);
			return;
		}
		log.debug("add header: {} = {}", header, value);
		requestTemplate.header(header, value);
	}
}
