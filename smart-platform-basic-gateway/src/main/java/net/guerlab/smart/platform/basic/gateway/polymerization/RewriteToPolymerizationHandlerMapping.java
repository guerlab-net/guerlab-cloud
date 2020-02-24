package net.guerlab.smart.platform.basic.gateway.polymerization;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.commons.collection.CollectionUtil;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.core.env.Environment;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.function.Predicate;

/**
 * 重写请求至聚合服务处理
 *
 * @author guer
 */
@Slf4j
public class RewriteToPolymerizationHandlerMapping extends RoutePredicateHandlerMapping {

    static final String GATEWAY_REWRITE_TO_POLYMERIZATION = "rewriteToPolymerization";

    private final RouteLocator routeLocator;

    private final DiscoveryLocatorProperties properties;

    private final RewriteToPolymerizationProperties rewriteToAllProperties;

    private final EvaluationContext evaluationContext;

    public RewriteToPolymerizationHandlerMapping(FilteringWebHandler webHandler, RouteLocator routeLocator,
            GlobalCorsProperties globalCorsProperties, Environment environment, DiscoveryLocatorProperties properties,
            RewriteToPolymerizationProperties rewriteToAllProperties) {
        super(webHandler, routeLocator, globalCorsProperties, environment);
        this.routeLocator = routeLocator;
        this.properties = properties;
        this.rewriteToAllProperties = rewriteToAllProperties;
        this.evaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().withInstanceMethods().build();
        setOrder(2);
    }

    private static ServiceInstance createServiceInstance(String serviceId, ServerHttpRequest request) {
        return new ServiceInstance() {

            @Override
            public String getServiceId() {
                return serviceId;
            }

            @Override
            public String getHost() {
                return serviceId;
            }

            @Override
            public int getPort() {
                return request.getURI().getPort();
            }

            @Override
            public boolean isSecure() {
                return request.getSslInfo() != null;
            }

            @Override
            public URI getUri() {
                return request.getURI();
            }

            @Override
            public Map<String, String> getMetadata() {
                return new HashMap<>(8);
            }
        };
    }

    private static Predicate<ServiceInstance> getIncludePredicate(String expressionString,
            EvaluationContext evaluationContext) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression includeExpr = parser.parseExpression(expressionString);

        if (Boolean.TRUE.toString().equalsIgnoreCase(expressionString)) {
            return instance -> true;
        } else {
            return instance -> Objects
                    .requireNonNullElse(includeExpr.getValue(evaluationContext, instance, Boolean.class), false);
        }
    }

    private static boolean polymerizationRouterAccept(String serviceId, Route route, String routeIdPrefix,
            RewriteToPolymerizationProperties rewriteToAllProperties) {
        IRewriteToPolymerizationProperties properties = rewriteToAllProperties.getProperties(serviceId);

        if (properties == null || !properties.getEnable()) {
            return false;
        }

        List<String> serviceNames = properties.getServiceNames();
        String routeId = route.getId();

        if (CollectionUtil.isEmpty(serviceNames) || Objects.equals(routeId, routeIdPrefix + serviceId)) {
            return false;
        }

        return serviceNames.stream().anyMatch(serviceName -> Objects.equals(routeId, routeIdPrefix + serviceName));
    }

    @Override
    protected Mono<Route> lookupRoute(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().toString();
        String[] paths = requestPath.substring(1).split("/");
        String serviceId = paths.length > 0 ? paths[0] : null;

        Predicate<ServiceInstance> includePredicate = getIncludePredicate(
                Optional.ofNullable(properties.getIncludeExpression()).orElse("true"), evaluationContext);
        ServiceInstance instance = createServiceInstance(serviceId, request);

        if (serviceId == null || !includePredicate.test(instance)) {
            return Mono.empty();
        }

        return this.routeLocator.getRoutes()
                .filter(route -> polymerizationRouterAccept(serviceId, route, properties.getRouteIdPrefix(),
                        rewriteToAllProperties)).next().map(route -> {
                    RewritePathGatewayFilterFactory.Config config = new RewritePathGatewayFilterFactory.Config();

                    config.setRegexp("/" + serviceId + "/(?<remaining>.*)");
                    config.setReplacement("/${remaining}");

                    return Route.async().asyncPredicate(route.getPredicate()).id(route.getId()).uri(route.getUri())
                            .order(route.getOrder()).filters(route.getFilters())
                            .filter(new RewriteToPolymerizationRewritePathGatewayFilterFactory(serviceId).apply(config))
                            .build();
                });
    }
}
