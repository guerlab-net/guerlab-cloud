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
import org.junit.jupiter.api.Test;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;

import net.guerlab.cloud.loadbalancer.properties.ClusterSameProperties;

/**
 * 相同集群规则测试.
 *
 * @author guer
 */
class ClusterSameRuleTest {

	static final String METADATA_KEY = "cluster";

	@Test
	void match() {
		ClusterSameProperties properties = new ClusterSameProperties();
		properties.setClusterName("local");
		properties.setMetadataKey(METADATA_KEY);
		ClusterSameRule rule = new ClusterSameRule(properties);

		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-local", Map.of(METADATA_KEY, "local")));
		instances.add(new MockServiceInstance("mock-remote", Map.of(METADATA_KEY, "remote")));

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());

		List<ServiceInstance> target = new ArrayList<>();
		target.add(new MockServiceInstance("mock-local", Map.of(METADATA_KEY, "local")));
		Assertions.assertIterableEquals(result, target);
	}

	@Test
	void notMatchWithNotAllowRollback() {
		ClusterSameProperties properties = new ClusterSameProperties();
		properties.setClusterName("local");
		properties.setMetadataKey(METADATA_KEY);
		ClusterSameRule rule = new ClusterSameRule(properties);

		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-local", Map.of(METADATA_KEY, "local2")));
		instances.add(new MockServiceInstance("mock-remote", Map.of(METADATA_KEY, "remote")));

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());
		Assertions.assertEquals(0, result.size());
	}

	@Test
	void notMatchWithAllowRollback() {
		ClusterSameProperties properties = new ClusterSameProperties();
		properties.setClusterName("local");
		properties.setMetadataKey(METADATA_KEY);
		properties.setAllowRollback(true);
		ClusterSameRule rule = new ClusterSameRule(properties);

		List<ServiceInstance> instances = new ArrayList<>();
		instances.add(new MockServiceInstance("mock-local", Map.of(METADATA_KEY, "local2")));
		instances.add(new MockServiceInstance("mock-remote", Map.of(METADATA_KEY, "remote")));

		List<ServiceInstance> result = rule.choose(instances, new DefaultRequest<>());
		Assertions.assertEquals(2, result.size());
	}
}
