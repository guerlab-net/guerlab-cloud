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

package net.guerlab.cloud.gateway.protect;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 接口保护自动配置.
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(ProtectProperties.class)
public class ProtectAutoConfigure {

	/**
	 * 构造接口保护过滤器.
	 *
	 * @param properties 接口保护过滤器配置
	 * @return 接口保护过滤器
	 */
	@Bean
	public ProtectFilter protectFilter(ProtectProperties properties) {
		return new ProtectFilter(properties);
	}
}
