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
package net.guerlab.cloud.server.openapi.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.server.openapi.properties.OpenApiProperties;
import net.guerlab.spring.web.properties.ResponseAdvisorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * OpenApi相关路径配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenApiAutoconfigure {

    @Autowired(required = false)
    public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties) {
        if (responseAdvisorProperties == null) {
            return;
        }

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