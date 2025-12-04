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

package net.guerlab.cloud.web.webflux.parse;

import java.net.InetSocketAddress;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.server.reactive.ServerHttpRequest;

import net.guerlab.cloud.commons.ip.IpParser;

/**
 * IP地址工具类.
 *
 * @author guer
 */
@Slf4j
public class ServerHttpRequestIpParse implements IpParser {

	@Override
	public boolean accept(Object request) {
		return request instanceof ServerHttpRequest;
	}

	@Nullable
	@Override
	public String getIpByHeader(Object request, String headerName) {
		String value = ((ServerHttpRequest) request).getHeaders().getFirst(headerName);
		log.debug("header: {} -> {}", headerName, value);
		return value;
	}

	@Nullable
	@Override
	public String getIpByRemoteAddress(Object request) {
		InetSocketAddress socketAddress = ((ServerHttpRequest) request).getRemoteAddress();
		return socketAddress != null ? socketAddress.getHostName() : null;
	}
}
