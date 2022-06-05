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

package net.guerlab.cloud.loadbalancer.properties;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import net.guerlab.cloud.loadbalancer.Constants;

/**
 * 负载均衡配置.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = Constants.PROPERTIES_PREFIX)
public class LoadBalancerProperties {

	/**
	 * 未匹配的时候返回空可用服务集合.
	 */
	private boolean noMatchReturnEmpty = false;

	/**
	 * 是否允许规则降级.<br>
	 * 当允许的时候规则处理返回了空或空集合的时，使用规则处理链返回上一个有效集合
	 */
	private boolean allowRuleReduce = false;

	/**
	 * 权重元信息字段列表.
	 */
	private List<String> weightMetadataKeys = Arrays.asList("nacos.weight", "service.weight", "weight");

}
