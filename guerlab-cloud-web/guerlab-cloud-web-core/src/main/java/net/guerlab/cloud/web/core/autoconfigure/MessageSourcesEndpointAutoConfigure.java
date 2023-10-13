/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.web.core.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.web.core.endpoints.MessageSourcesEndpoint;

/**
 * 消息源监控端点自动配置.
 *
 * @author guer
 */
@AutoConfiguration
public class MessageSourcesEndpointAutoConfigure {

	/**
	 * 创建消息源监控端点实例.
	 *
	 * @param messageSource 消息源
	 * @return 消息源监控端点实例
	 */
	@Bean
	public MessageSourcesEndpoint messageSourcesEndpoint(MessageSource messageSource) {
		return new MessageSourcesEndpoint(messageSource);
	}
}
