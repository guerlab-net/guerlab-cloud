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

package net.guerlab.cloud.web.webflux;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import net.guerlab.cloud.core.Constants;
import net.guerlab.cloud.core.result.Result;

/**
 * @author guer
 */
@SuppressWarnings("UastIncorrectHttpHeaderInspection")
@SpringBootTest(
		classes = TestApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class WebFluxHeaderTestApplicationTests {

	@LocalServerPort // 随机端口
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHeadersEndpoint() {
		// 构造请求头（可以自定义大小写）
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		requestHeaders.add(Constants.ALLOW_TRANSFER_HEADER_PREFIX + "UpperCaseKey", "v");
		requestHeaders.add(Constants.ALLOW_TRANSFER_HEADER_PREFIX + "lowerCaseKey", "v");

		// 发送 GET 请求到 /headers 接口
		String url = "http://localhost:" + port + "/headers";

		RequestEntity<Void> entity = new RequestEntity<>(requestHeaders, HttpMethod.GET, URI.create(url));

		ResponseEntity<TestResult> response = restTemplate.exchange(entity, TestResult.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

		TestResult testResult = response.getBody();
		Assertions.assertNotNull(testResult);

		Map<String, String> result = testResult.getData();
		Assertions.assertNotNull(result);

		Map<String, String> target = new HashMap<>();
		target.put(Constants.ALLOW_TRANSFER_HEADER_PREFIX + "uppercasekey", "v");
		target.put(Constants.ALLOW_TRANSFER_HEADER_PREFIX + "lowercasekey", "v");

		Assertions.assertIterableEquals(target.entrySet(), result.entrySet());
	}

	static class TestResult extends Result<Map<String, String>> {

	}
}
