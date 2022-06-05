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

import org.apache.commons.lang3.StringUtils;

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
	 * 通过配置初始化相同集群规则.
	 *
	 * @param properties 配置
	 */
	public ClusterSameRule(ClusterSameProperties properties) {
		super(properties);
	}

	@Override
	public List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request) {
		String clusterName = StringUtils.trimToNull(properties.getClusterName());

		if (clusterName == null) {
			return instances;
		}

		return instances.stream()
				.filter(instance -> clusterName.equalsIgnoreCase(getInstanceClusterName(instance)))
				.toList();
	}

	private String getInstanceClusterName(ServiceInstance instance) {
		return instance.getMetadata().get(properties.getMetadataKey());
	}
}
