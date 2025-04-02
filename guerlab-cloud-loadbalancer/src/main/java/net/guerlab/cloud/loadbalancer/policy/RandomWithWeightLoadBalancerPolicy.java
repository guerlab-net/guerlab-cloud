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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.util.CollectionUtils;

import net.guerlab.cloud.loadbalancer.properties.LoadBalancerProperties;
import net.guerlab.cloud.loadbalancer.utils.Chooser;
import net.guerlab.cloud.loadbalancer.utils.Pair;

/**
 * 加权随机负载均衡策略.
 *
 * @author guer
 */
@Slf4j
public class RandomWithWeightLoadBalancerPolicy extends AbstractLoadBalancerPolicy {

	/**
	 * 默认权重.
	 */
	private static final double DEFAULT_WEIGHT = 1.0;

	/**
	 * 负载均衡配置.
	 */
	private final LoadBalancerProperties properties;

	/**
	 * 创建加权随机负载均衡策略.
	 *
	 * @param properties 负载均衡配置
	 */
	public RandomWithWeightLoadBalancerPolicy(LoadBalancerProperties properties) {
		this.properties = properties;
	}

	@Nullable
	@Override
	protected ServiceInstance choose0(List<ServiceInstance> instances) {
		List<Pair<ServiceInstance>> instancesWithWeight = new ArrayList<>();
		for (ServiceInstance instance : instances) {
			instancesWithWeight.add(new Pair<>(instance, getWeight(instance.getMetadata())));
		}
		Chooser<String, ServiceInstance> chooser = new Chooser<>("randomWithWeight");
		chooser.refresh(instancesWithWeight);
		return chooser.randomWithWeight();
	}

	/**
	 * 获取权重.
	 *
	 * @param metadata 元信息
	 * @return 权重
	 */
	private double getWeight(@Nullable Map<String, String> metadata) {
		List<String> weightMetadataKeys = properties.getWeightMetadataKeys();
		if (CollectionUtils.isEmpty(metadata) || CollectionUtils.isEmpty(weightMetadataKeys)) {
			return DEFAULT_WEIGHT;
		}

		Double weight;
		for (String metadataKey : weightMetadataKeys) {
			weight = parseWeight(metadata, metadataKey);
			if (weight != null) {
				return weight;
			}
		}
		return DEFAULT_WEIGHT;
	}

	@Nullable
	private static Double parseWeight(Map<String, String> metadata, String key) {
		try {
			return Double.parseDouble(metadata.get(key));
		}
		catch (Exception e) {
			return null;
		}
	}
}
