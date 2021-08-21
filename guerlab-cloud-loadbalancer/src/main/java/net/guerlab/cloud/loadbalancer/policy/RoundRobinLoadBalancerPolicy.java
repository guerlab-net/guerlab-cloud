package net.guerlab.cloud.loadbalancer.policy;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询负载均衡策略
 *
 * @author guer
 */
public class RoundRobinLoadBalancerPolicy implements LoadBalancerPolicy {

    private final AtomicInteger position = new AtomicInteger(new Random().nextInt(1000));

    @Override
    public ServiceInstance choose(@Nullable List<ServiceInstance> instances) {
        if (instances == null || instances.isEmpty()) {
            return null;
        } else if (instances.size() == 1) {
            return instances.get(0);
        }

        int pos = Math.abs(this.position.incrementAndGet());
        return instances.get(pos % instances.size());
    }
}
