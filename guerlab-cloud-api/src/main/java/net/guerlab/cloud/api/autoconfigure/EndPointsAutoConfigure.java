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

package net.guerlab.cloud.api.autoconfigure;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.api.endpoints.FeignClientEndpoint;

/**
 * 监控端点自动配置.
 *
 * @author guer
 */
@AutoConfiguration
@ConditionalOnClass(Endpoint.class)
public class EndPointsAutoConfigure {

	/**
	 * 构造FeignClient实例监控端点.
	 *
	 * @return FeignClient实例监控端点
	 */
	@Bean
	public FeignClientEndpoint feignClientEndpoint() {
		return new FeignClientEndpoint();
	}
}
