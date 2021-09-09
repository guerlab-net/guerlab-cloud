package net.guerlab.cloud.loadbalancer.policy;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 抽象负载均衡策略
 *
 * @author guer
 */
public abstract class AbstractLoadBalancerPolicy implements LoadBalancerPolicy {

    @Override
    public final ServiceInstance choose(@Nullable List<ServiceInstance> instances) {
        if (instances == null || instances.isEmpty()) {
            return null;
        } else if (instances.size() == 1) {
            return instances.get(0);
        }
        return choose0(instances);
    }

    /**
     * 根据策略选择实例
     *
     * @param instances
     *         实例列表
     * @return 实例
     */
    @Nullable
    protected abstract ServiceInstance choose0(List<ServiceInstance> instances);
}
