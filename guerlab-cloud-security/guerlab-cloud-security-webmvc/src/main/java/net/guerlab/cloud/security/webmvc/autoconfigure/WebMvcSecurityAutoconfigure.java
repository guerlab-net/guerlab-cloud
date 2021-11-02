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
package net.guerlab.cloud.security.webmvc.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.autoconfigure.AuthorizePathAutoconfigure;
import net.guerlab.cloud.security.core.properties.DefaultCorsConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * WebMvc安全配置
 *
 * @author guer
 */
@Slf4j
@Order(99)
@Configuration
@AutoConfigureAfter(AuthorizePathAutoconfigure.class)
public class WebMvcSecurityAutoconfigure extends WebSecurityConfigurerAdapter {

    private final ObjectProvider<CorsConfiguration> configProvider;

    private final ObjectProvider<AuthorizePathProvider> authorizePathProviders;

    public WebMvcSecurityAutoconfigure(ObjectProvider<CorsConfiguration> configProvider,
            ObjectProvider<AuthorizePathProvider> authorizePathProviders) {
        this.configProvider = configProvider;
        this.authorizePathProviders = authorizePathProviders;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().formLogin();
        http.csrf().disable();
        http.cors().configurationSource(request -> configProvider.getIfAvailable(DefaultCorsConfiguration::new));

        authorizePathProviders.stream()
                .forEach(provider -> authorizePathConfig(http, provider.httpMethod(), provider.paths()));

        http.authorizeRequests().anyRequest().permitAll();
    }

    private void authorizePathConfig(HttpSecurity http, @Nullable HttpMethod httpMethod, List<String> paths) {
        if (CollectionUtils.isEmpty(paths)) {
            return;
        }

        log.debug("authorizePathConfig[method: {}, paths: {}]", httpMethod, paths);

        try {
            if (httpMethod == null) {
                http.authorizeRequests().antMatchers(paths.toArray(new String[0])).authenticated();
            } else {
                http.authorizeRequests().antMatchers(httpMethod, paths.toArray(new String[0])).authenticated();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
