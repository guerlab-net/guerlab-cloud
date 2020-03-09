package net.guerlab.smart.platform.basic.gateway.vc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 版本控制自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(VersionControlProperties.class)
public class VersionControlAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(VersionControlFilter.class)
    public VersionControlFilter versionControlFilter(LoadBalancerClient loadBalancer,
            LoadBalancerProperties properties) {
        return new VersionControlFilter(loadBalancer, properties);
    }
}
