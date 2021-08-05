package net.guerlab.cloud.loadbalancer.rule;

import net.guerlab.cloud.loadbalancer.properties.ClusterSameProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 相同集群策略
 *
 * @author guer
 */
public class ClusterSameRule extends BaseRule<ClusterSameProperties> {

    /**
     * 集群名称
     */
    private final String clusterName;

    public ClusterSameRule(String clusterName, ClusterSameProperties properties) {
        super(properties);
        this.clusterName = clusterName;
    }

    @Override
    public List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request) {
        return instances.stream()
                .filter(instance -> clusterName.equalsIgnoreCase(instance.getMetadata().get("nacos.cluster")))
                .collect(Collectors.toList());
    }
}
