package net.guerlab.cloud.loadbalancer.policy;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询负载均衡策略
 *
 * @author guer
 */
public class RoundRobinLoadBalancerPolicy extends AbstractLoadBalancerPolicy {

    private final AtomicInteger position = new AtomicInteger(new Random().nextInt(1000));

    @Override
    protected ServiceInstance choose0(List<ServiceInstance> instances) {
        int pos = Math.abs(this.position.incrementAndGet());
        return instances.get(pos % instances.size());
    }
}
