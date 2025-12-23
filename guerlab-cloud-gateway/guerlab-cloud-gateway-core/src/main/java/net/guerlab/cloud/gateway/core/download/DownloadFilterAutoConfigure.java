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

package net.guerlab.cloud.gateway.core.download;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.gateway.core.base.CorsProperties;

/**
 * 下载扩展支持自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
public class DownloadFilterAutoConfigure {

	/**
	 * 构造下载扩展支持.
	 *
	 * @param properties 配置文件
	 * @return 下载扩展支持
	 */
	@Bean
	public DownloadFilter downloadFilter(CorsProperties properties) {
		return new DownloadFilter(properties);
	}
}
