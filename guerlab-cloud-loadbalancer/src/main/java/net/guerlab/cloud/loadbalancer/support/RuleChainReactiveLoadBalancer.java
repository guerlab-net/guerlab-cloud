package net.guerlab.cloud.loadbalancer.support;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;

import java.util.List;

/**
 * 基于规则链的负责均衡
 *
 * @author guer
 */
public interface RuleChainReactiveLoadBalancer extends ReactorServiceInstanceLoadBalancer {

    /**
     * 选择实例
     *
     * @param instances
     *         实例列表
     * @param request
     *         请求
     * @return 实例
     */
    ServiceInstance choose(List<ServiceInstance> instances, Request<?> request);
}
