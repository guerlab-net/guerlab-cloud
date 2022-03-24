/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;

import net.guerlab.cloud.loadbalancer.properties.ClusterSameProperties;

/**
 * 相同集群规则.
 *
 * @author guer
 */
public class ClusterSameRule extends BaseRule<ClusterSameProperties> {

	/**
	 * 集群名称.
	 */
	private final String clusterName;

	/**
	 * 通过集群名称和相同集群配置初始化相同集群规则.
	 *
	 * @param clusterName
	 *         集群名称
	 * @param properties
	 *         相同集群配置
	 */
	public ClusterSameRule(String clusterName, ClusterSameProperties properties) {
		super(properties);
		this.clusterName = clusterName;
	}

	@Override
	public List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request) {
		return instances.stream()
				.filter(instance -> clusterName.equalsIgnoreCase(instance.getMetadata().get("nacos.cluster")))
				.collect(Collectors.toList());
	}
}
