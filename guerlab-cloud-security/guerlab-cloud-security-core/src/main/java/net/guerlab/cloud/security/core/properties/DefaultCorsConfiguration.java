package net.guerlab.cloud.security.core.properties;

import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * 默认Core配置
 *
 * @author guer
 */
public class DefaultCorsConfiguration extends CorsConfiguration {

    public DefaultCorsConfiguration() {
        setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        setMaxAge(1800L);
        setAllowCredentials(true);
    }
}
