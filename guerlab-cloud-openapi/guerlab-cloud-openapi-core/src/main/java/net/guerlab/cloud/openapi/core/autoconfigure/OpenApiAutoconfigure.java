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
package net.guerlab.cloud.openapi.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.openapi.core.properties.OpenApiProperties;
import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.SimpleAuthorizePathProvider;
import net.guerlab.cloud.security.core.autoconfigure.AuthorizePathAutoconfigure;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springdoc.core.Constants.SPRINGDOC_ENABLED;

/**
 * OpenApi相关路径配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
@AutoConfigureBefore(AuthorizePathAutoconfigure.class)
public class OpenApiAutoconfigure {

    /**
     * 根据SpringDocConfigProperties构建授权路径提供者
     *
     * @param properties
     *         SpringDocConfigProperties
     * @return 授权路径提供者
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
    public AuthorizePathProvider openApiAuthorizePathProvider(SpringDocConfigProperties properties) {
        String path = StringUtils.trimToEmpty(properties.getApiDocs().getPath()) + "/**";
        return new SimpleAuthorizePathProvider(Collections.singletonList(path));
    }

    @Autowired(required = false)
    public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties) {
        // @formatter:off
        List<String> excluded = Arrays.asList(
                "org.springdoc.webmvc.api.OpenApiWebMvcResource#openapiJson",
                "org.springdoc.webmvc.api.OpenApiWebMvcResource#openapiYaml",
                "org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource#openapiJson",
                "org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource#openapiYaml",
                "org.springdoc.webflux.api.OpenApiWebfluxResource#openapiJson",
                "org.springdoc.webflux.api.OpenApiWebfluxResource#openapiYaml",
                "org.springdoc.webflux.api.MultipleOpenApiWebFluxResource#openapiJson",
                "org.springdoc.webflux.api.MultipleOpenApiWebFluxResource#openapiYaml"
        );
        // @formatter:on

        log.debug("add excluded: {}", excluded);

        responseAdvisorProperties.addExcluded(excluded);
    }
}
