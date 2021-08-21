package net.guerlab.cloud.loadbalancer.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient负载均衡配置
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(name = "org.springframework.web.reactive.function.client.WebClient")
public class LoadBalancedWebClientBuilderAutoconfigure {

    /**
     * 构造具有负载均衡支持的http请求客户端构造器
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
