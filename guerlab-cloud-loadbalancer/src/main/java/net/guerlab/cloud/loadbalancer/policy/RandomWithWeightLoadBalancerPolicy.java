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

package net.guerlab.cloud.loadbalancer.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.lang.Nullable;

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
	 * 权重key列表.
	 */
	private static final List<String> WEIGHT_METADATA_KEYS = Arrays.asList("nacos.weight", "service.weight", "weight");

	/**
	 * 获取权重.
	 *
	 * @param metadata 元信息
	 * @return 权重
	 */
	private static double getWeight(@Nullable Map<String, String> metadata) {
		if (metadata == null || metadata.isEmpty()) {
			return DEFAULT_WEIGHT;
		}

		return WEIGHT_METADATA_KEYS.stream().map(key -> parseWeight(metadata, key)).filter(Objects::nonNull).findFirst()
				.orElse(DEFAULT_WEIGHT);
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
}
