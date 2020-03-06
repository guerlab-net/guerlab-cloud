package net.guerlab.smart.platform.basic.gateway.gray;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 灰度控制自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(GrayLoadBalancerClientFilterProperties.class)
public class GrayLoadBalancerClientFilterAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(GrayLoadBalancerClientFilter.class)
    public GrayLoadBalancerClientFilter grayLoadBalancerClientFilter(LoadBalancerClient loadBalancer,
            LoadBalancerProperties properties) {
        return new GrayLoadBalancerClientFilter(loadBalancer, properties);
    }
}
