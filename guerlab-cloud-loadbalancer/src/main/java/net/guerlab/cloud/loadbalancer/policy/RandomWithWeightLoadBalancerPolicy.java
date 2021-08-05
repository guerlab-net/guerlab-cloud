package net.guerlab.cloud.loadbalancer.policy;

import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 加权随机负载均衡策略
 *
 * @author guer
 */
@Slf4j
public class RandomWithWeightLoadBalancerPolicy implements LoadBalancerPolicy {

    @Override
    public ServiceInstance choose(List<ServiceInstance> instances) {
        if (instances == null || instances.isEmpty()) {
            return null;
        } else if (instances.size() == 1) {
            return instances.get(0);
        }
        List<Pair<ServiceInstance>> instancesWithWeight = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            instancesWithWeight.add(new Pair<>(instance, getWeight(instance)));
        }
        Chooser<String, ServiceInstance> vipChooser = new Chooser<>("randomWithWeight");
        vipChooser.refresh(instancesWithWeight);
        return vipChooser.randomWithWeight();
    }

    private double getWeight(ServiceInstance instance) {
        Map<String, String> metadata = instance.getMetadata();
        if (metadata == null || metadata.isEmpty()) {
            return 1;
        }

        try {
            return Double.parseDouble(metadata.get("nacos.weight"));
        } catch (Exception e) {
            return 1;
        }
    }
}
