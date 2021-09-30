package net.guerlab.cloud.loadbalancer.policy;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机负载均衡策略
 *
 * @author guer
 */
public class RandomLoadBalancerPolicy extends AbstractLoadBalancerPolicy {

    @Override
    protected ServiceInstance choose0(List<ServiceInstance> instances) {
        return instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
    }
}
