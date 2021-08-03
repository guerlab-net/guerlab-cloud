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

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import net.guerlab.cloud.loadbalancer.properties.ClusterSameProperties;
import net.guerlab.cloud.loadbalancer.properties.LoadBalancerProperties;
import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;
import net.guerlab.cloud.loadbalancer.rule.ClusterSameRule;
import net.guerlab.cloud.loadbalancer.rule.IRule;
import net.guerlab.cloud.loadbalancer.rule.VersionMatchRule;
import net.guerlab.cloud.loadbalancer.support.CustomerLoadBalancerClientFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.config.BlockingLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * 全局负载均衡自定义配置
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(NacosDiscoveryAutoConfiguration.class)
@AutoConfigureBefore({ BlockingLoadBalancerClientAutoConfiguration.class, LoadBalancerAutoConfiguration.class })
@EnableConfigurationProperties({ LoadBalancerProperties.class, VersionControlProperties.class,
        ClusterSameProperties.class })
@LoadBalancerClients(defaultConfiguration = CustomerLoadBalancerClientConfiguration.class)
public class GlobalLoadBalancerAutoConfiguration {

    private final ObjectProvider<List<LoadBalancerClientSpecification>> configurations;

    public GlobalLoadBalancerAutoConfiguration(ObjectProvider<List<LoadBalancerClientSpecification>> configurations) {
        this.configurations = configurations;
    }

    /**
     * 构造负载均衡客户端工厂
     *
     * @return 负载均衡客户端工厂
     */
    @Bean
    public LoadBalancerClientFactory customerLoadBalancerClientFactory() {
        CustomerLoadBalancerClientFactory clientFactory = new CustomerLoadBalancerClientFactory();
        clientFactory.setConfigurations(this.configurations.getIfAvailable(Collections::emptyList));
        return clientFactory;
    }

    /**
     * 构造版本匹配规则
     *
     * @param properties
     *         版本匹配规则配置
     * @return 版本匹配规则
     */
    @Bean
    public IRule versionMatchRule(VersionControlProperties properties) {
        return new VersionMatchRule(properties);
    }

    /**
     * 构造相同集群策略
     *
     * @param nacosDiscoveryProperties
     *         Nacos发现配置
     * @param properties
     *         相同集群配置
     * @return 相同集群策略
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public IRule clusterSameRule(NacosDiscoveryProperties nacosDiscoveryProperties, ClusterSameProperties properties) {
        String clusterName = nacosDiscoveryProperties.getClusterName();
        return new ClusterSameRule(clusterName, properties);
    }
}
