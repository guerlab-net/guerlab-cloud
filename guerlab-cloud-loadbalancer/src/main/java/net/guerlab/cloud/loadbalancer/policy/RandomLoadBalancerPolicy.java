package net.guerlab.cloud.loadbalancer.policy;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机负载均衡策略
 *
 * @author guer
 */
public class RandomLoadBalancerPolicy implements LoadBalancerPolicy {

    @Override
    public ServiceInstance choose(@Nullable List<ServiceInstance> instances) {
        if (instances == null || instances.isEmpty()) {
            return null;
        } else if (instances.size() == 1) {
            return instances.get(0);
        }
        return instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
    }
}
