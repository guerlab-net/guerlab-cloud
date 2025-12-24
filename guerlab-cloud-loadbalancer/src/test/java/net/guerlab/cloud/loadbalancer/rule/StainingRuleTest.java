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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;

import net.guerlab.cloud.context.core.TransferContext;
import net.guerlab.cloud.core.Constants;
import net.guerlab.cloud.loadbalancer.properties.StainingProperties;

/**
 * 染色规则测试.
 *
 * @author guer
 */
class StainingRuleTest {

	static StainingRule rule;

	@BeforeAll
	static void before() {
		rule = new StainingRule(new StainingProperties());
	}

	@Test
	void singleMetadataMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-v1", Map.of("env", "v1")));
		instances.add(new MockServiceInstance("mock-v2", Map.of("env", "v2")));

		TransferContext.setTransfer(Constants.STAINING_KEY + "env", "v1");

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());

		List<ServiceInstance> target = new ArrayList<>();
		target.add(new MockServiceInstance("mock-v1", Map.of("env", "v1")));
		Assertions.assertIterableEquals(result, target);
	}

	@Test
	void singleMetadataNotMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-v1", Map.of("env", "v1")));
		instances.add(new MockServiceInstance("mock-v2", Map.of("env", "v2")));

		TransferContext.setTransfer(Constants.STAINING_KEY + "env", "v3");

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());
		Assertions.assertEquals(2, result.size());
	}

	@Test
	void multiMetadataMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-test-v1", Map.of("env", "test", "version", "v1")));
		instances.add(new MockServiceInstance("mock-test-v2", Map.of("env", "test", "version", "v2")));
		instances.add(new MockServiceInstance("mock-prod-v1", Map.of("env", "prod", "version", "v1")));
		instances.add(new MockServiceInstance("mock-prod-v2", Map.of("env", "prod", "version", "v2")));

		TransferContext.setTransfer(Constants.STAINING_KEY + "env", "test");
		TransferContext.setTransfer(Constants.STAINING_KEY + "version", "v2");

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());

		List<ServiceInstance> target = new ArrayList<>();
		target.add(new MockServiceInstance("mock-test-v2", Map.of("env", "test", "version", "v2")));
		Assertions.assertIterableEquals(result, target);
	}

	@Test
	void multiMetadataNotMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-test-v1", Map.of("env", "test", "version", "v1")));
		instances.add(new MockServiceInstance("mock-test-v2", Map.of("env", "test", "version", "v2")));
		instances.add(new MockServiceInstance("mock-prod-v1", Map.of("env", "prod", "version", "v1")));
		instances.add(new MockServiceInstance("mock-prod-v2", Map.of("env", "prod", "version", "v2")));

		TransferContext.setTransfer(Constants.STAINING_KEY + "env", "dev");
		TransferContext.setTransfer(Constants.STAINING_KEY + "version", "v3");

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());
		Assertions.assertEquals(4, result.size());
	}

	@Test
	void multiMetadataPartMatch() {
		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-test-v1", Map.of("env", "test", "version", "v1")));
		instances.add(new MockServiceInstance("mock-test-v2", Map.of("env", "test", "version", "v2")));
		instances.add(new MockServiceInstance("mock-prod-v1", Map.of("env", "prod", "version", "v1")));
		instances.add(new MockServiceInstance("mock-prod-v2", Map.of("env", "prod", "version", "v2")));

		TransferContext.setTransfer(Constants.STAINING_KEY + "env", "test");
		TransferContext.setTransfer(Constants.STAINING_KEY + "version", "v3");

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());
		Assertions.assertEquals(2, result.size());
	}
}
