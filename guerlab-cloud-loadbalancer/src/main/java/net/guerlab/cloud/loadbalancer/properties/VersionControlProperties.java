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

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import net.guerlab.cloud.loadbalancer.Constants;

/**
 * 版本控制配置.
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@RefreshScope
@ConfigurationProperties(prefix = VersionControlProperties.PROPERTIES_PREFIX)
public class VersionControlProperties extends BaseRuleProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = Constants.PROPERTIES_PREFIX + ".version-control";

	/**
	 * 请求头关键字.
	 */
	private String requestKey = "version";

	/**
	 * 服务元信息关键字.
	 */
	private String metadataKey = "request-version-control";
}
