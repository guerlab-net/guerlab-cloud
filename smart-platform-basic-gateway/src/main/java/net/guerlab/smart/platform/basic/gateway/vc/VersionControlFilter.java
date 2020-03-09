package net.guerlab.smart.platform.basic.gateway.vc;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 版本控制过滤器
 *
 * @author guer
 */
public class VersionControlFilter extends LoadBalancerClientFilter {

    private DiscoveryClient discoveryClient;

    private VersionControlProperties properties;

    public VersionControlFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        String requestKey = StringUtils.trimToNull(properties.getRequestKey());
        String metadataKey = StringUtils.trimToNull(properties.getMetadataKey());

        if (requestKey == null || metadataKey == null) {
            return super.choose(exchange);
        }
        Version requestVersion = parseVersion(requestKey, exchange.getRequest());

        if (requestVersion == null) {
            return super.choose(exchange);
        }

        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        if (uri == null) {
            return super.choose(exchange);
        }
        String instancesId = uri.getHost();

        List<ServiceInstance> instances = discoveryClient.getInstances(instancesId);

        if (instances == null || instances.isEmpty()) {
            return super.choose(exchange);
        }

        // 空列表
        List<ServiceInstance> hasNotVersions = new ArrayList<>(instances.size());
        // 相等列表
        List<ServiceInstance> equalsVersions = new ArrayList<>(instances.size());
        // 可匹配列表
        List<ServiceInstance> matchVersions = new ArrayList<>(instances.size());

        // 按照解析出的版本号信息进行分组
        instances.forEach(instance -> {
            Version version = Version.parse(instance.getMetadata().get(metadataKey));
            if (version == null) {
                hasNotVersions.add(instance);
            } else if (version.equals(requestVersion)) {
                equalsVersions.add(instance);
            } else if (version.match(requestVersion)) {
                matchVersions.add(instance);
            }
            // 其他情况进行忽略
        });

        if (!matchVersions.isEmpty()) {
            return choose0(matchVersions);
        } else if (!equalsVersions.isEmpty()) {
            return choose0(equalsVersions);
        } else if (!hasNotVersions.isEmpty()) {
            return choose0(hasNotVersions);
        } else {
            return null;
        }
    }

    private ServiceInstance choose0(List<ServiceInstance> instanceList) {
        return instanceList.get(RandomUtils.nextInt(0, instanceList.size()));
    }

    private Version parseVersion(String requestKey, ServerHttpRequest request) {
        String versionString = StringUtils.trimToNull(request.getHeaders().getFirst(requestKey));
        if (versionString == null) {
            versionString = StringUtils.trimToNull(request.getQueryParams().getFirst(requestKey));
        }

        return Version.parse(versionString, false);
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setProperties(VersionControlProperties properties) {
        this.properties = properties;
    }
}
