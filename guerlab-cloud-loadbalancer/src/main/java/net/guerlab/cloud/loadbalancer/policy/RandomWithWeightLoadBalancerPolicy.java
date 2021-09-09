package net.guerlab.cloud.loadbalancer.policy;

import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * 加权随机负载均衡策略
 *
 * @author guer
 */
@Slf4j
public class RandomWithWeightLoadBalancerPolicy extends AbstractLoadBalancerPolicy {

    /**
     * 默认权重
     */
    private static final double DEFAULT_WEIGHT = 1.0;

    /**
     * 权重key列表
     */
    private static final List<String> WEIGHT_METADATA_KEYS = Arrays.asList("nacos.weight", "service.weight", "weight");

    private static double getWeight(@Nullable Map<String, String> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            return DEFAULT_WEIGHT;
        }

        return WEIGHT_METADATA_KEYS.stream().map(key -> parseWeight(metadata, key)).filter(Objects::nonNull).findFirst()
                .orElse(DEFAULT_WEIGHT);
    }

    @Nullable
    private static Double parseWeight(Map<String, String> metadata, String key) {
        try {
            return Double.parseDouble(metadata.get(key));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected ServiceInstance choose0(List<ServiceInstance> instances) {
        List<Pair<ServiceInstance>> instancesWithWeight = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            instancesWithWeight.add(new Pair<>(instance, getWeight(instance.getMetadata())));
        }
        Chooser<String, ServiceInstance> chooser = new Chooser<>("randomWithWeight");
        chooser.refresh(instancesWithWeight);
        return chooser.randomWithWeight();
    }
}
