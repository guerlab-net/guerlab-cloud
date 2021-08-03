package net.guerlab.cloud.loadbalancer.rule;

import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;
import net.guerlab.cloud.loadbalancer.utils.VersionCompareUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本匹配
 *
 * @author guer
 */
@SuppressWarnings("unchecked")
public class VersionMatchRule extends BaseRule<VersionControlProperties> {

    public VersionMatchRule(VersionControlProperties properties) {
        super(properties);
    }

    @Override
    public List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request) {
        String requestKey = StringUtils.trimToNull(properties.getRequestKey());
        String metadataKey = StringUtils.trimToNull(properties.getMetadataKey());

        Request<RequestDataContext> lbRequest;
        try {
            lbRequest = (Request<RequestDataContext>) request;
        } catch (Exception e) {
            return instances;
        }

        String requestVersion = lbRequest.getContext().getClientRequest().getHeaders().getFirst(requestKey);
        if (requestVersion == null) {
            return instances;
        }

        List<ServiceInstance> list = getInstancesWithRequestVersion(instances, requestVersion, metadataKey);
        return list != null ? list : instances;
    }

    private List<ServiceInstance> getInstancesWithRequestVersion(List<ServiceInstance> instances, String requestVersion,
            String metadataKey) {
        // 空列表
        List<ServiceInstance> hasNotVersions = new ArrayList<>(instances.size());
        // 相等列表
        List<ServiceInstance> equalsVersions = new ArrayList<>(instances.size());
        // 可匹配列表
        List<ServiceInstance> matchVersions = new ArrayList<>(instances.size());

        // 按照解析出的版本号信息进行分组
        instances.forEach(instance -> {
            String version = instance.getMetadata().get(metadataKey);
            if (version == null) {
                hasNotVersions.add(instance);
            } else if (version.equals(requestVersion)) {
                equalsVersions.add(instance);
            } else if (VersionCompareUtils.match(requestVersion, version)) {
                matchVersions.add(instance);
            }
            // 其他情况进行忽略
        });

        if (!equalsVersions.isEmpty()) {
            return equalsVersions;
        } else if (!matchVersions.isEmpty()) {
            return matchVersions;
        } else if (!hasNotVersions.isEmpty()) {
            return hasNotVersions;
        } else {
            return null;
        }
    }
}
