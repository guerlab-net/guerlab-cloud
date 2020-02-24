package net.guerlab.smart.platform.basic.gateway.polymerization;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * 重新至聚合服务路径重新过滤器工厂
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class RewriteToPolymerizationRewritePathGatewayFilterFactory extends RewritePathGatewayFilterFactory
        implements Ordered {

    private final String serviceId;

    public RewriteToPolymerizationRewritePathGatewayFilterFactory(String serviceId) {
        this.serviceId = "/" + serviceId;
    }

    @Override
    public GatewayFilter apply(Config config) {
        String replacement = config.getReplacement().replace("$\\", "$");
        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            addOriginalRequestUrl(exchange, req.getURI());
            String path = req.getURI().getRawPath();
            String newPath = Objects.equals(serviceId, path) ? "/" : path.replaceAll(config.getRegexp(), replacement);

            ServerHttpRequest request = req.mutate().path(newPath).build();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());

            return chain.filter(exchange.mutate().request(request).build());
        }, 2);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
