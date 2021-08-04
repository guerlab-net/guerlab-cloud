package net.guerlab.cloud.loadbalancer.support;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.loadbalancer.policy.LoadBalancerPolicy;
import net.guerlab.cloud.loadbalancer.properties.LoadBalancerProperties;
import net.guerlab.cloud.loadbalancer.rule.IRule;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于规则链的负责均衡的默认实现
 *
 * @author guer
 */
@Slf4j
public class DefaultRuleChainReactiveLoadBalancer implements RuleChainReactiveLoadBalancer {

    private final String serviceId;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final ObjectProvider<List<IRule>> ruleProvider;

    private final LoadBalancerProperties loadBalancerProperties;

    private final LoadBalancerPolicy policy;

    public DefaultRuleChainReactiveLoadBalancer(String serviceId,
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            ObjectProvider<List<IRule>> ruleProvider, LoadBalancerProperties loadBalancerProperties,
            LoadBalancerPolicy policy) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.ruleProvider = ruleProvider;
        this.loadBalancerProperties = loadBalancerProperties;
        this.policy = policy;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = getSupplier();
        return supplier.get(request).next().map(instances -> {
            Response<ServiceInstance> response = buildResponse(instances, request);
            if (supplier instanceof SelectedInstanceCallback && response.hasServer()) {
                ((SelectedInstanceCallback) supplier).selectedServiceInstance(response.getServer());
            }
            return response;
        });
    }

    private ServiceInstanceListSupplier getSupplier() {
        return serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
    }

    private Response<ServiceInstance> buildResponse(List<ServiceInstance> instances, Request<?> request) {
        ServiceInstance instance;
        if (instances.isEmpty()) {
            return emptyResponse();
        } else if ((instance = choose(instances, request)) != null) {
            return new DefaultResponse(instance);
        } else if (loadBalancerProperties.isNoMatchReturnEmpty()) {
            return emptyResponse();
        } else {
            return new DefaultResponse(policy.choose(instances));
        }
    }

    private Response<ServiceInstance> emptyResponse() {
        log.debug("No servers available for service: " + serviceId);
        return new EmptyResponse();
    }

    @Override
    public ServiceInstance choose(List<ServiceInstance> instances, Request<?> request) {
        List<ServiceInstance> list = ruleFilter(instances, request);

        if (list == null || list.isEmpty()) {
            return null;
        }

        return policy.choose(list);
    }

    /**
     * 根据规则链过滤实例
     *
     * @param instances
     *         实例列表
     * @param request
     *         请求
     * @return 过滤后的实例列表
     */
    private List<ServiceInstance> ruleFilter(List<ServiceInstance> instances, Request<?> request) {
        List<IRule> rules = ruleProvider.getIfUnique(Collections::emptyList).stream().filter(IRule::isEnabled).sorted()
                .collect(Collectors.toList());

        if (rules.isEmpty()) {
            return instances;
        }

        List<ServiceInstance> currentList = instances;
        List<ServiceInstance> preList;

        for (IRule rule : rules) {
            preList = currentList;
            currentList = rule.choose(currentList, request);
            log.debug("invoke rule: {}", rule);
            if (currentList == null || currentList.isEmpty()) {
                log.debug("rule return instances is empty");
                if (loadBalancerProperties.isAllowRuleReduce()) {
                    return preList;
                } else {
                    return null;
                }
            }
        }

        return currentList;
    }
}
