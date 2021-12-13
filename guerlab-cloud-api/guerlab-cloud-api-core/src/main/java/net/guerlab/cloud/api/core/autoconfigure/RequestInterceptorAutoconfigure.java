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
package net.guerlab.cloud.api.core.autoconfigure;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.api.core.debug.DebugProxyRequestInterceptor;
import net.guerlab.cloud.api.core.loadbalancer.LoadBalancerHeaderRequestInterceptor;
import net.guerlab.cloud.api.core.properties.DebugProperties;
import net.guerlab.cloud.loadbalancer.autoconfigure.GlobalLoadBalancerAutoConfiguration;
import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 请求拦截器自动配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DebugProperties.class)
@AutoConfigureAfter(GlobalLoadBalancerAutoConfiguration.class)
public class RequestInterceptorAutoconfigure {

    /**
     * 构建负载均衡版本控制请求头注入拦截器
     *
     * @param properties
     *         版本控制配置
     * @param discoveryProperties
     *         服务发现配置
     * @return 负载均衡版本控制请求头注入拦截器
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public RequestInterceptor loadBalancerHeaderRequestInterceptor(VersionControlProperties properties,
            NacosDiscoveryProperties discoveryProperties) {
        return new LoadBalancerHeaderRequestInterceptor(properties, discoveryProperties);
    }

    /**
     * 构建开发代理请求拦截器
     *
     * @param properties
     *         开发模式配置
     * @return 开发代理请求拦截器
     */
    @Bean
    public RequestInterceptor debugProxyRequestInterceptor(DebugProperties properties) {
        return new DebugProxyRequestInterceptor(properties);
    }

}
