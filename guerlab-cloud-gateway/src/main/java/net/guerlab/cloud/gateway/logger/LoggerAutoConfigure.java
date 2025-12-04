/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.gateway.logger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动配置.
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
public class LoggerAutoConfigure {

	/**
	 * 构造请求日志过滤器.
	 *
	 * @param properties 请求日志过滤器配置
	 * @return 请求日志过滤器
	 */
	@Bean
	public RequestTimeFilter requestTimeFilter(LoggerProperties properties) {
		return new RequestTimeFilter(properties);
	}
}
