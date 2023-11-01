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

package net.guerlab.cloud.api.autoconfigure;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.api.debug.DebugProxyRequestInterceptor;
import net.guerlab.cloud.api.loadbalancer.LoadBalancerHeaderRequestInterceptor;
import net.guerlab.cloud.api.properties.DebugProperties;
import net.guerlab.cloud.loadbalancer.autoconfigure.GlobalLoadBalancerAutoConfiguration;
import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;

/**
 * 请求拦截器自动配置.
 *
 * @author guer
 */
@Slf4j
@EnableConfigurationProperties(DebugProperties.class)
@AutoConfiguration(after = GlobalLoadBalancerAutoConfiguration.class)
public class RequestInterceptorAutoConfigure {

	/**
	 * 构建负载均衡版本控制请求头注入拦截器.
	 *
	 * @param properties 版本控制配置
	 * @return 负载均衡版本控制请求头注入拦截器
	 */
	@Bean
	public RequestInterceptor loadBalancerHeaderRequestInterceptor(VersionControlProperties properties) {
		return new LoadBalancerHeaderRequestInterceptor(properties);
	}

	/**
	 * 构建开发代理请求拦截器.
	 *
	 * @param properties 开发模式配置
	 * @return 开发代理请求拦截器
	 */
	@Bean
	public RequestInterceptor debugProxyRequestInterceptor(DebugProperties properties) {
		return new DebugProxyRequestInterceptor(properties);
	}

}
