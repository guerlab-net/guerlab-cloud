/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.loadbalancer.autoconfigure;

import net.guerlab.cloud.loadbalancer.policy.LoadBalancerPolicy;
import net.guerlab.cloud.loadbalancer.properties.LoadBalancerProperties;
import net.guerlab.cloud.loadbalancer.rule.IRule;
import net.guerlab.cloud.loadbalancer.support.RuleChainReactiveLoadBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * 自定义负载均衡客户端配置
 *
 * @author guer
 */
@AutoConfigureBefore(LoadBalancerClientConfiguration.class)
public class CustomerLoadBalancerClientConfiguration {

    /**
     * 构造服务实例的负载均衡器
     *
     * @param environment
     *         系统环境
     * @param loadBalancerClientFactory
     *         负载均衡客户端工厂类
     * @param ruleProvider
     *         规则对象提供
     * @param loadBalancerProperties
     *         负载均衡配置
     * @param policy
     *         负载均衡策略
     * @return 服务实例的负载均衡器
     */
    @Bean
    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory, ObjectProvider<List<IRule>> ruleProvider,
            LoadBalancerProperties loadBalancerProperties, LoadBalancerPolicy policy) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RuleChainReactiveLoadBalancer(name,
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), ruleProvider,
                loadBalancerProperties, policy);
    }
}
