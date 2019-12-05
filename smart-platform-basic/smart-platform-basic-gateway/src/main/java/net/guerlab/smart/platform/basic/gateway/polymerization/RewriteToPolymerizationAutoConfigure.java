package net.guerlab.smart.platform.basic.gateway.polymerization;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.Nullable;

/**
 * 重新请求至聚合服务自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(RewriteToPolymerizationProperties.class)
public class RewriteToPolymerizationAutoConfigure {

    @Bean
    @Conditional(RewriteToAllCondition.class)
    public RewriteToPolymerizationHandlerMapping rewriteToAllHandlerMapping(FilteringWebHandler webHandler,
            RouteLocator routeLocator, GlobalCorsProperties globalCorsProperties, Environment environment,
            DiscoveryLocatorProperties properties, RewriteToPolymerizationProperties rewriteToAllProperties) {
        return new RewriteToPolymerizationHandlerMapping(webHandler, routeLocator, globalCorsProperties, environment,
                properties, rewriteToAllProperties);
    }

    @SuppressWarnings("WeakerAccess")
    static class RewriteToAllCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, @Nullable AnnotatedTypeMetadata metadata) {
            Boolean enable = context.getEnvironment()
                    .getProperty("spring.cloud.gateway.rewrite-to-polymerization.enable", Boolean.class);
            return enable == null ? false : enable;
        }
    }
}
