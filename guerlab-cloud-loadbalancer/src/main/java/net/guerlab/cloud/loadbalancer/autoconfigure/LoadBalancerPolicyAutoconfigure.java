package net.guerlab.cloud.loadbalancer.autoconfigure;

import net.guerlab.cloud.loadbalancer.Constants;
import net.guerlab.cloud.loadbalancer.policy.LoadBalancerPolicy;
import net.guerlab.cloud.loadbalancer.policy.RandomLoadBalancerPolicy;
import net.guerlab.cloud.loadbalancer.policy.RandomWithWeightLoadBalancerPolicy;
import net.guerlab.cloud.loadbalancer.policy.RoundRobinLoadBalancerPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 负载均衡策略自动配置
 *
 * @author guer
 */
@Configuration
public class LoadBalancerPolicyAutoconfigure {

    /**
     * 构造轮询负载均衡策略
     *
     * @return 轮询负载均衡策略
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = Constants.PROPERTIES_PREFIX, name = Constants.PROPERTIES_POLICY, havingValue = "roundRobin", matchIfMissing = true)
    public LoadBalancerPolicy roundRobinLoadBalancerPolicy() {
        return new RoundRobinLoadBalancerPolicy();
    }

    /**
     * 构造加权随机负载均衡策略
     *
     * @return 加权随机负载均衡策略
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = Constants.PROPERTIES_PREFIX, name = Constants.PROPERTIES_POLICY, havingValue = "randomWithWeight")
    public LoadBalancerPolicy randomWithWeightLoadBalancerPolicy() {
        return new RandomWithWeightLoadBalancerPolicy();
    }

    /**
     * 构造随机负载均衡策略
     *
     * @return 随机负载均衡策略
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = Constants.PROPERTIES_PREFIX, name = Constants.PROPERTIES_POLICY, havingValue = "random")
    public LoadBalancerPolicy randomLoadBalancerPolicy() {
        return new RandomLoadBalancerPolicy();
    }
}