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

package net.guerlab.cloud.loadbalancer.policy;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

/**
 * 随机负载均衡策略.
 *
 * @author guer
 */
public class RandomLoadBalancerPolicy extends AbstractLoadBalancerPolicy {

	private final SecureRandom secureRandom = new SecureRandom();

	@Override
	protected ServiceInstance choose0(List<ServiceInstance> instances) {
		return instances.get(secureRandom.nextInt(instances.size()));
	}
}
