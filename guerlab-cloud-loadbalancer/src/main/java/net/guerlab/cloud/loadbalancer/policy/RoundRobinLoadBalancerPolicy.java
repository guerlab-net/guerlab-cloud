/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.client.ServiceInstance;

/**
 * 轮询负载均衡策略.
 *
 * @author guer
 */
public class RoundRobinLoadBalancerPolicy extends AbstractLoadBalancerPolicy {

	/**
	 * 当前偏移量.
	 */
	private final AtomicInteger position;

	/**
	 * 创建轮询负载均衡策略.
	 */
	public RoundRobinLoadBalancerPolicy() {
		Random random = new Random();
		position = new AtomicInteger(random.nextInt(1000));
	}

	@Override
	protected ServiceInstance choose0(List<ServiceInstance> instances) {
		int pos = Math.abs(this.position.incrementAndGet());
		return instances.get(pos % instances.size());
	}
}
