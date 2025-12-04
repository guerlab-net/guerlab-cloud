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

package net.guerlab.cloud.gateway.base;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

/**
 * 基础设置自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class BaseAutoConfigure {

	/**
	 * 构造cors过滤器.
	 *
	 * @param properties 配置文件
	 * @return cors过滤器
	 */
	@Bean
	public WebFilter corsFilter(CorsProperties properties) {
		return new CorsFilter(properties);
	}
}
