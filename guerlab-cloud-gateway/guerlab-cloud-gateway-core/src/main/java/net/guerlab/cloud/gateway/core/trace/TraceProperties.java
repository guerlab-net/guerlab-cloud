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

package net.guerlab.cloud.gateway.core.trace;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import net.guerlab.cloud.core.Constants;

/**
 * 链路追踪配置.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = TraceProperties.PROPERTIES_PREFIX)
public class TraceProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = "spring.cloud.gateway.trace";

	/**
	 * 上游Header的key.
	 */
	private String upstreamHeaderKey = Constants.TRACE_ID_KEY;
}
