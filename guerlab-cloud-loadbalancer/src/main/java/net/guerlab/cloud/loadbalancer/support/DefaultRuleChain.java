package net.guerlab.cloud.loadbalancer.support;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.loadbalancer.properties.LoadBalancerProperties;
import net.guerlab.cloud.loadbalancer.rule.IRule;
import net.guerlab.cloud.loadbalancer.rule.IRuleChain;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 默认规则链
 *
 * @author guer
 */
@Slf4j
public class DefaultRuleChain implements IRuleChain, ReactorServiceInstanceLoadBalancer {

    private final String serviceId;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final ObjectProvider<List<IRule>> ruleProvider;

    private final LoadBalancerProperties loadBalancerProperties;

    private final AtomicInteger position;

    public DefaultRuleChain(String serviceId,
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            ObjectProvider<List<IRule>> ruleProvider, LoadBalancerProperties loadBalancerProperties) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.ruleProvider = ruleProvider;
        this.loadBalancerProperties = loadBalancerProperties;
        this.position = new AtomicInteger(new Random().nextInt(1000));
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = getSupplier();
        return supplier.get(request).next().map(instances -> {
            ServiceInstance instance = choose(instances, request);

            Response<ServiceInstance> serviceInstanceResponse;
            if (instance != null) {
                serviceInstanceResponse = new DefaultResponse(instance);
            } else if (loadBalancerProperties.isNoMatchReturnEmpty()) {
                serviceInstanceResponse = emptyResponse();
            } else {
                serviceInstanceResponse = buildInstanceResponse(instances);
            }

            if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
                ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
            }
            return serviceInstanceResponse;
        });
    }

    private ServiceInstanceListSupplier getSupplier() {
        return serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
    }

    private Response<ServiceInstance> emptyResponse() {
        log.debug("No servers available for service: " + serviceId);
        return new EmptyResponse();
    }

    private Response<ServiceInstance> buildInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            return emptyResponse();
        }
        int pos = Math.abs(this.position.incrementAndGet());
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }

    @Override
    public ServiceInstance choose(List<ServiceInstance> instances, Request<?> request) {
        List<IRule> rules = ruleProvider.getIfUnique(Collections::emptyList).stream().filter(IRule::isEnabled).sorted()
                .collect(Collectors.toList());

        List<ServiceInstance> list = instances;

        if (!rules.isEmpty()) {
            for (IRule rule : rules) {
                list = rule.choose(list, request);
                log.debug("invoke rule: {}", rule);
                if (list == null || list.isEmpty()) {
                    log.debug("rule return instance is empty");
                    return null;
                }
            }
        }

        int pos = Math.abs(this.position.incrementAndGet());
        return list.get(pos % list.size());
    }
}
