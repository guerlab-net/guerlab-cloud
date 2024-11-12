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

package net.guerlab.cloud.web.webflux.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

import net.guerlab.cloud.web.webflux.filter.TransferHeaderFilter;

/**
 * 透传header处理自动配置.
 *
 * @author guer
 */
@AutoConfiguration
public class TransferHeaderFilterAutoConfigure {

	/**
	 * 初始化透传header处理.
	 *
	 * @param requestMappingHandlerMapping requestMappingHandlerMapping
	 * @return 透传header处理
	 */
	@Bean
	public TransferHeaderFilter transferHeaderFilter(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		return new TransferHeaderFilter(requestMappingHandlerMapping);
	}
}
