/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import net.guerlab.cloud.loadbalancer.Constants;

/**
 * 相同集群配置.
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@RefreshScope
@ConfigurationProperties(prefix = ClusterSameProperties.PROPERTIES_PREFIX)
public class ClusterSameProperties extends BaseRuleProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = Constants.PROPERTIES_PREFIX + ".cluster-same";

	/**
	 * 集群名称.
	 */
	private String clusterName;

	/**
	 * 集群元信息字段.
	 */
	private String metadataKey = "cluster";
}
