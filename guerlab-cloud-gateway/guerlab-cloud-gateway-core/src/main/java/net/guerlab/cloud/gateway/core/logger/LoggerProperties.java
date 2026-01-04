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

package net.guerlab.cloud.gateway.core.logger;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 日志配置.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = LoggerProperties.PROPERTIES_PREFIX)
public class LoggerProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = "spring.cloud.gateway.logger";

	/**
	 * 是否允许记录请求时间.
	 */
	private boolean enableRecordRequestTime;
}
