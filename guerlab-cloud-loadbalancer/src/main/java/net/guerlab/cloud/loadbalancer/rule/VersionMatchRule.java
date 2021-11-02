/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.loadbalancer.rule;

import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;
import net.guerlab.cloud.loadbalancer.utils.VersionCompareUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本匹配规则
 *
 * @author guer
 */
@SuppressWarnings("unchecked")
public class VersionMatchRule extends BaseRule<VersionControlProperties> {

    /**
     * 通过版本控制配置初始化版本匹配规则
     *
     * @param properties
     *         版本控制配置
     */
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

    /**
     * 通过请求版本获取实例列表
     *
     * @param instances
     *         原始实例列表
     * @param requestVersion
     *         请求版本
     * @param metadataKey
     *         版本元信息key
     * @return 实例列表
     */
    @Nullable
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
