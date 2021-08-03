package net.guerlab.cloud.loadbalancer.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;

import java.util.List;

/**
 * 规则链
 *
 * @author guer
 */
public interface IRuleChain {

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
