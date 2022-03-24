/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import java.util.stream.Collectors;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.config.BlockingLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.loadbalancer.properties.LoadBalancerProperties;
import net.guerlab.cloud.loadbalancer.support.CustomerLoadBalancerClientFactory;

/**
 * 全局负载均衡自定义配置.
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(NacosDiscoveryAutoConfiguration.class)
@AutoConfigureBefore({BlockingLoadBalancerClientAutoConfiguration.class, LoadBalancerAutoConfiguration.class})
@EnableConfigurationProperties({LoadBalancerProperties.class, LoadBalancerClientsProperties.class})
@LoadBalancerClients(defaultConfiguration = CustomerLoadBalancerClientConfiguration.class)
public class GlobalLoadBalancerAutoConfiguration {

	/**
	 * 负载均衡客户端说明列表提供者.
	 */
	private final ObjectProvider<LoadBalancerClientSpecification> configurations;

	/**
	 * 通过负载均衡客户端说明列表提供者初始化全局负载均衡自定义配置.
	 *
	 * @param configurations
	 *         负载均衡客户端说明列表提供者
	 */
	public GlobalLoadBalancerAutoConfiguration(ObjectProvider<LoadBalancerClientSpecification> configurations) {
		this.configurations = configurations;
	}

	/**
	 * 构造负载均衡客户端工厂.
	 *
	 * @param properties
	 *         负载均衡客户端配置
	 * @return 负载均衡客户端工厂
	 */
	@Bean
	public LoadBalancerClientFactory customerLoadBalancerClientFactory(LoadBalancerClientsProperties properties) {
		CustomerLoadBalancerClientFactory clientFactory = new CustomerLoadBalancerClientFactory(properties);
		clientFactory.setConfigurations(configurations.stream().collect(Collectors.toList()));
		return clientFactory;
	}
}
