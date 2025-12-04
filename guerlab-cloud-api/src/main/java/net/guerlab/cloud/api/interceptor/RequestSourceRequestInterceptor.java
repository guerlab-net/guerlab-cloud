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

package net.guerlab.cloud.api.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.core.util.SpringUtils;

/**
 * 请求源请求拦截器.
 *
 * @author guer
 */
@Slf4j
public class RequestSourceRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header(Constants.HTTP_HEADER_REQUEST_SOURCE, SpringUtils.getApplicationName());
	}
}
