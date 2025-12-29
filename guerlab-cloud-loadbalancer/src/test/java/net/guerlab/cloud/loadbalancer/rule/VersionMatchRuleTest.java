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

package net.guerlab.cloud.loadbalancer.rule;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;

/**
 * 版本匹配规则测试.
 *
 * @author guer
 */
class VersionMatchRuleTest {

	static final String requestKey = "version";

	static final String metadataKey = "request-version-control";

	static VersionMatchRule rule;

	@BeforeAll
	static void before() {
		VersionControlProperties properties = new VersionControlProperties();
		properties.setRequestKey(requestKey);
		properties.setMetadataKey(metadataKey);

		rule = new VersionMatchRule(properties);
	}

	Request<RequestDataContext> buildRequest(String version) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(requestKey, version);

		RequestData requestData = new RequestData(
				HttpMethod.GET,
				URI.create("http://localhost/mockPath"),
				headers,
				new LinkedMultiValueMap<>(),
				new HashMap<>()
		);

		RequestDataContext context = new RequestDataContext(requestData);

		return new DefaultRequest<>(context);
	}

	@Test
	void singleMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[1.0, 2.0)")));
		instances.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[2.0, 3.0)")));

		List<ServiceInstance> result = rule.choose(instances, buildRequest("1.0"));

		List<ServiceInstance> target = new ArrayList<>();
		target.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[1.0, 2.0)")));
		Assertions.assertIterableEquals(target, result);
	}

	@Test
	void multiMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[1.0, 2.0)")));
		instances.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[2.0, 3.0)")));
		instances.add(new MockServiceInstance("mock-v3", Map.of(metadataKey, "[1.0, 3.0)")));

		List<ServiceInstance> result = rule.choose(instances, buildRequest("1.0"));

		List<ServiceInstance> target = new ArrayList<>();
		target.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[1.0, 2.0)")));
		target.add(new MockServiceInstance("mock-v3", Map.of(metadataKey, "[1.0, 3.0)")));
		Assertions.assertIterableEquals(target, result);
	}

	@Test
	void notMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[1.0, 2.0)")));
		instances.add(new MockServiceInstance("mock-v1", Map.of(metadataKey, "[2.0, 3.0)")));

		List<ServiceInstance> result = rule.choose(instances, buildRequest("3.0"));
		Assertions.assertEquals(2, result.size());
	}
}
