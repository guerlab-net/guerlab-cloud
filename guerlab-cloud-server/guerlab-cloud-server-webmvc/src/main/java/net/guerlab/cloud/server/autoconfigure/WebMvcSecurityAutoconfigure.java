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

import net.guerlab.spring.web.autoconfigure.SecurityAutoconfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * WebMvc安全配置
 *
 * @author guer
 */
@Configuration
public class WebMvcSecurityAutoconfigure extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAutoconfigure.class);

    private final ObjectProvider<CorsConfiguration> configProvider;

    private final ObjectProvider<WebEndpointProperties> webEndpointPropertiesProvider;

    public WebMvcSecurityAutoconfigure(ObjectProvider<CorsConfiguration> configProvider,
            ObjectProvider<WebEndpointProperties> webEndpointPropertiesProvider) {
        this.configProvider = configProvider;
        this.webEndpointPropertiesProvider = webEndpointPropertiesProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().formLogin();
        http.csrf().disable();
        http.cors().configurationSource(request -> configProvider.getIfAvailable(DefaultCorsConfiguration::new));

        WebEndpointProperties webEndpointProperties = webEndpointPropertiesProvider.getIfUnique();
        if (webEndpointProperties != null) {
            String path = webEndpointProperties.getBasePath() + "/**";
            LOGGER.debug("actuator security config: {} with authenticated", path);
            http.authorizeRequests().antMatchers(path).authenticated();
        }

        http.authorizeRequests().anyRequest().permitAll();
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
