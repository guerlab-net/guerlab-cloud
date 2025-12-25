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

package net.guerlab.cloud.commons.ip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * ip地址获取测试.
 *
 * @author guer
 */
class GetIpTest {

	static final String IP_ADDRESS = "127.0.0.1";

	static Stream<Arguments> requestProvider() {
		List<Arguments> arguments = new ArrayList<>();

		arguments.add(Arguments.arguments(buildRequest("X-Forwarded-For")));
		arguments.add(Arguments.arguments(buildRequest("Cdn-Src-Ip")));
		arguments.add(Arguments.arguments(buildRequest("Proxy-Client-IP")));
		arguments.add(Arguments.arguments(buildRequest("WL-Proxy-Client-IP")));
		arguments.add(Arguments.arguments(buildRequest("HTTP_CLIENT_IP")));
		arguments.add(Arguments.arguments(buildRequest("HTTP_X_FORWARDED_FOR")));
		arguments.add(Arguments.arguments(buildRequest("X-Real-IP")));
		arguments.add(Arguments.arguments(buildRequest("CustomerHeader")));

		return arguments.stream();
	}

	private static HttpServletRequest buildRequest(String header) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(header, IP_ADDRESS);

		return request;
	}

	@ParameterizedTest
	@MethodSource("requestProvider")
	void getByHeader(HttpServletRequest request) {
		String result = IpUtils.getIp(request);
		Assertions.assertEquals(IP_ADDRESS, result);
	}

	@Test
	void getByRemote() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr(IP_ADDRESS);

		String result = IpUtils.getIp(request);
		Assertions.assertEquals(IP_ADDRESS, result);
	}
}
