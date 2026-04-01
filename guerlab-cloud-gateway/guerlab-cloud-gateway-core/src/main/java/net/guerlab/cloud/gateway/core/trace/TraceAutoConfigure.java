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

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 链路追踪自动配置.
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(TraceProperties.class)
public class TraceAutoConfigure {

	/**
	 * 构造链路追踪拦截器.
	 *
	 * @param properties 链路追踪配置
	 * @return 链路追踪拦截器
	 */
	@Bean
	public TraceFilter traceFilter(TraceProperties properties) {
		return new TraceFilter(properties);
	}
}
