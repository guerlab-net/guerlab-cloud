package net.guerlab.cloud.loadbalancer.policy;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 负载均衡策略
 *
 * @author guer
 */
public interface LoadBalancerPolicy {

    /**
     * 根据策略选择实例
     *
     * @param instances
     *         实例列表
     * @return 实例
     */
    @Nullable
    ServiceInstance choose(@Nullable List<ServiceInstance> instances);
}
