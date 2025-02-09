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

import jakarta.annotation.Nullable;

import org.springframework.cloud.client.ServiceInstance;

/**
 * 负载均衡策略.
 *
 * @author guer
 */
public interface LoadBalancerPolicy {

	/**
	 * 根据策略选择实例.
	 *
	 * @param instances 实例列表
	 * @return 实例
	 */
	@Nullable
	ServiceInstance choose(@Nullable List<ServiceInstance> instances);
}
