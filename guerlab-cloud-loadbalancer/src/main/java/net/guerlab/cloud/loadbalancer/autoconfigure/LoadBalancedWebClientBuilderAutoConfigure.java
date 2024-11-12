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

package net.guerlab.cloud.loadbalancer.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient负载均衡配置.
 *
 * @author guer
 */
@AutoConfiguration
@ConditionalOnClass(name = "org.springframework.web.reactive.function.client.WebClient")
public class LoadBalancedWebClientBuilderAutoConfigure {

	/**
	 * 构造具有负载均衡支持的http请求客户端构造器.
	 *
	 * @return 具有负载均衡支持的http请求客户端构造器
	 */
	@Bean
	@LoadBalanced
	@ConditionalOnMissingBean
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
}
