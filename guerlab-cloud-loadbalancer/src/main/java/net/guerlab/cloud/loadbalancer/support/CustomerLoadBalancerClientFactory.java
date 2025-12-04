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

package net.guerlab.cloud.loadbalancer.support;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;

import net.guerlab.cloud.loadbalancer.autoconfigure.CustomerLoadBalancerClientConfiguration;

/**
 * 自定义负载均衡客户端工厂.
 *
 * @author guer
 */
@Slf4j
public class CustomerLoadBalancerClientFactory extends LoadBalancerClientFactory {

	/**
	 * 初始化自定义负载均衡客户端工厂.
	 *
	 * @param properties LoadBalancerClientsProperties
	 */
	public CustomerLoadBalancerClientFactory(LoadBalancerClientsProperties properties) {
		super(properties);
		Class<CustomerLoadBalancerClientConfiguration> clazz = CustomerLoadBalancerClientConfiguration.class;

		LoadBalancerClientSpecification specification = new LoadBalancerClientSpecification();
		specification.setName("default." + clazz.getName());
		specification.setConfiguration(new Class[] {clazz});

		setConfigurations(Collections.singletonList(specification));
	}
}
