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
import java.util.Objects;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;

import net.guerlab.cloud.context.core.TransferContext;
import net.guerlab.cloud.core.Constants;
import net.guerlab.cloud.loadbalancer.properties.StainingProperties;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 染色规则.
 *
 * @author guer
 */
@Slf4j
public class StainingRule extends BaseRule<StainingProperties> {

	/**
	 * 通过配置初始化染色规则.
	 *
	 * @param properties 配置
	 */
	public StainingRule(StainingProperties properties) {
		super(properties);
	}

	@Override
	protected int defaultOrder() {
		return 2;
	}

	@Override
	public List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request) {
		Map<String, String> headerMap = TransferContext.getAllTransfer();

		if (headerMap.isEmpty()) {
			return instances;
		}

		List<ServiceInstance> result = new ArrayList<>(instances);

		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			String header = parseStainingHeader(entry.getKey());
			String matchValue = StringUtils.trimToNull(entry.getValue());
			if (header == null || matchValue == null) {
				continue;
			}

			log.debug("staining filter, header: {}, matchValue: {}", header, matchValue);

			List<ServiceInstance> afterList = result.stream()
					.filter(instance -> instanceFilter(instance, header, matchValue))
					.toList();
			if (!afterList.isEmpty()) {
				result = afterList;
			}
		}

		return result;
	}

	@Nullable
	private static String parseStainingHeader(String header) {
		if (!header.startsWith(Constants.STAINING_HEADER_PREFIX)) {
			return null;
		}

		return StringUtils.trimToNull(header.substring(Constants.STAINING_HEADER_PREFIX.length()));
	}

	private static boolean instanceFilter(ServiceInstance instance, String header, String matchValue) {
		Map<String, String> metadata = instance.getMetadata();
		if (CollectionUtil.isEmpty(metadata)) {
			return false;
		}

		String instanceValue = metadata.get(header);
		log.debug("instanceValue: {}, matchValue: {}", instance, matchValue);
		return Objects.equals(matchValue, instanceValue);
	}
}
