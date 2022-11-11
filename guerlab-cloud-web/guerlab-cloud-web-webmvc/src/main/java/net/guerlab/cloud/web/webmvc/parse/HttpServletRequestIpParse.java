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

package net.guerlab.cloud.web.webmvc.parse;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.commons.ip.IpParser;

/**
 * IP地址工具类.
 *
 * @author guer
 */
@Slf4j
public class HttpServletRequestIpParse implements IpParser {

	@Override
	public boolean accept(Object request) {
		return request instanceof HttpServletRequest;
	}

	@Nullable
	@Override
	public String getIpByHeader(Object request, String headerName) {
		String value = ((HttpServletRequest) request).getHeader(headerName);
		log.debug("header: {} -> {}", headerName, value);
		return value;
	}

	@Nullable
	@Override
	public String getIpByRemoteAddress(Object request) {
		return ((HttpServletRequest) request).getRemoteAddr();
	}
}
