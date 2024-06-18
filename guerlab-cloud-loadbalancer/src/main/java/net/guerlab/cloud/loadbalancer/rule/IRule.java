/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import jakarta.annotation.Nullable;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.core.Ordered;

/**
 * 规则.
 *
 * @author guer
 */
public interface IRule extends Ordered, Comparable<IRule> {

	/**
	 * 是否启用.
	 *
	 * @return 是否启用
	 */
	boolean isEnabled();

	/**
	 * 选择实例.
	 *
	 * @param instances 实例列表
	 * @param request   请求
	 * @return 实例列表
	 */
	@Nullable
	List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request);

	/**
	 * 默认排序方式.
	 *
	 * @param other 其他策略
	 * @return 排序结果
	 */
	@Override
	default int compareTo(IRule other) {
		return getOrder() - other.getOrder();
	}
}
