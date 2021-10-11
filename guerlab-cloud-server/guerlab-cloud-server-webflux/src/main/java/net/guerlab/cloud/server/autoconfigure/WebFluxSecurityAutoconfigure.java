/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.server.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * WebFlux安全配置
 *
 * @author guer
 */
@Slf4j
@Configuration
public class WebFluxSecurityAutoconfigure {

    private final ObjectProvider<CorsConfiguration> configProvider;

    private final ObjectProvider<WebEndpointProperties> webEndpointPropertiesProvider;

    public WebFluxSecurityAutoconfigure(ObjectProvider<CorsConfiguration> configProvider,
            ObjectProvider<WebEndpointProperties> webEndpointPropertiesProvider) {
        this.configProvider = configProvider;
        this.webEndpointPropertiesProvider = webEndpointPropertiesProvider;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.httpBasic().and().formLogin();
        http.csrf().disable();
        http.cors().configurationSource(request -> configProvider.getIfAvailable(DefaultCorsConfiguration::new));

        WebEndpointProperties webEndpointProperties = webEndpointPropertiesProvider.getIfUnique();
        if (webEndpointProperties != null) {
            String path = webEndpointProperties.getBasePath() + "/**";
            log.debug("actuator security config: {} with authenticated", path);
            http.authorizeExchange().pathMatchers(path).authenticated();
        }

        http.authorizeExchange().anyExchange().permitAll();

        return http.build();
    }

    private static class DefaultCorsConfiguration extends CorsConfiguration {

        public DefaultCorsConfiguration() {
            setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
            setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
            setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
            setMaxAge(1800L);
            setAllowCredentials(true);
        }
    }

}
