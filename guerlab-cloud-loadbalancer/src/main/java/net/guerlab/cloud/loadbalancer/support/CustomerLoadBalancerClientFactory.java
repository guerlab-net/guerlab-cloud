package net.guerlab.cloud.loadbalancer.support;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.loadbalancer.autoconfigure.CustomerLoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;

import java.util.Collections;

/**
 * 自定义负载均衡客户端工厂
 *
 * @author guer
 */
@Slf4j
public class CustomerLoadBalancerClientFactory extends LoadBalancerClientFactory {

    public CustomerLoadBalancerClientFactory() {
        Class<CustomerLoadBalancerClientConfiguration> clazz = CustomerLoadBalancerClientConfiguration.class;

        LoadBalancerClientSpecification specification = new LoadBalancerClientSpecification();
        specification.setName("default." + clazz.getName());
        specification.setConfiguration(new Class[] { clazz });

        setConfigurations(Collections.singletonList(specification));
    }
}
