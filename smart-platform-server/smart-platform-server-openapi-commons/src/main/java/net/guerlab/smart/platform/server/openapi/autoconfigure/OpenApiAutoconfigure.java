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
package net.guerlab.smart.platform.server.openapi.autoconfigure;

import net.guerlab.smart.platform.server.openapi.properties.OpenApiProperties;
import net.guerlab.spring.web.properties.ResponseAdvisorProperties;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * OpenApi相关路径配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ OpenApiProperties.class })
@ComponentScan("net.guerlab.smart.platform.server.openapi")
public class OpenApiAutoconfigure {

    @Autowired(required = false)
    public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties,
            SpringDocConfigProperties properties, OpenApiProperties openApiProperties) {
        if (responseAdvisorProperties == null) {
            return;
        }
        if (properties != null) {
            responseAdvisorProperties.addExcluded(Collections.singletonList(properties.getApiDocs().getPath()));
        }
        if (openApiProperties != null && openApiProperties.getCloud() != null) {
            String path = StringUtils.trimToNull(openApiProperties.getCloud().getPath());
            if (path != null) {
                responseAdvisorProperties.addExcluded();
            }
        }
    }

}
