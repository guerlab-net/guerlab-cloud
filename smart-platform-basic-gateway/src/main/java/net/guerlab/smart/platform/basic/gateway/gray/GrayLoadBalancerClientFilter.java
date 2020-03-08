package net.guerlab.smart.platform.basic.gateway.gray;

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
import java.util.List;
import java.util.stream.Collectors;

/**
 * 灰度控制
 *
 * @author guer
 */
public class GrayLoadBalancerClientFilter extends LoadBalancerClientFilter {

    private DiscoveryClient discoveryClient;

    private GrayLoadBalancerClientFilterProperties properties;

    public GrayLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
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

        List<ServiceInstance> instanceList = instances.stream().filter(instance -> {
            Version instanceVersion = Version.parse(instance.getMetadata().get(metadataKey), false);
            return instanceVersion != null && instanceVersion.match(requestVersion);
        }).collect(Collectors.toList());

        if (instanceList.isEmpty()) {
            instanceList = instances.stream().filter(instance -> {
                Version instanceVersion = Version.parse(instance.getMetadata().get(metadataKey), false);
                return instanceVersion != null && instanceVersion.match(requestVersion);
            }).collect(Collectors.toList());
        }

        if (instanceList.isEmpty()) {
            return null;
        }

        return instanceList.get(RandomUtils.nextInt(0, instanceList.size()));
    }

    private Version parseVersion(String requestKey, ServerHttpRequest request) {
        String versionString = StringUtils.trimToNull(request.getHeaders().getFirst(requestKey));
        if (versionString == null) {
            versionString = StringUtils.trimToNull(request.getQueryParams().getFirst(requestKey));
        }

        return Version.parse(versionString, true);
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setProperties(GrayLoadBalancerClientFilterProperties properties) {
        this.properties = properties;
    }
}
