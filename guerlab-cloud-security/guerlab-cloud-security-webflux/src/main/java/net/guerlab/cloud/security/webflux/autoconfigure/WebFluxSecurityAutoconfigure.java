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
package net.guerlab.cloud.security.webflux.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.autoconfigure.AuthorizePathAutoconfigure;
import net.guerlab.cloud.security.core.properties.DefaultCorsConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * WebFlux安全配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@AutoConfigureAfter(AuthorizePathAutoconfigure.class)
public class WebFluxSecurityAutoconfigure {

    private final ObjectProvider<CorsConfiguration> configProvider;

    private final ObjectProvider<AuthorizePathProvider> authorizePathProviders;

    public WebFluxSecurityAutoconfigure(ObjectProvider<CorsConfiguration> configProvider,
            ObjectProvider<AuthorizePathProvider> authorizePathProviders) {
        this.configProvider = configProvider;
        this.authorizePathProviders = authorizePathProviders;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.httpBasic().and().formLogin();
        http.csrf().disable();
        http.cors().configurationSource(request -> configProvider.getIfAvailable(DefaultCorsConfiguration::new));

        authorizePathProviders.stream()
                .forEach(provider -> authorizePathConfig(http, provider.httpMethod(), provider.paths()));

        http.authorizeExchange().anyExchange().permitAll();

        return http.build();
    }

    private void authorizePathConfig(ServerHttpSecurity http, @Nullable HttpMethod httpMethod, List<String> paths) {
        if (CollectionUtils.isEmpty(paths)) {
            return;
        }

        log.debug("authorizePathConfig[method: {}, paths: {}]", httpMethod, paths);

        if (httpMethod == null) {
            http.authorizeExchange().pathMatchers(paths.toArray(new String[0])).authenticated();
        } else {
            http.authorizeExchange().pathMatchers(httpMethod, paths.toArray(new String[0])).authenticated();
        }
    }

}
