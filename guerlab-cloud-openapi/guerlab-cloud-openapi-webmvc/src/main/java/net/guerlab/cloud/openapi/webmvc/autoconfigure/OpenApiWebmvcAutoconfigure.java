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
package net.guerlab.cloud.openapi.webmvc.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * OpenApi相关路径配置
 *
 * @author guer
 */
@Slf4j
@Configuration
public class OpenApiWebmvcAutoconfigure {

    @Autowired(required = false)
    public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties) {
        // @formatter:off
        List<String> excluded = Collections.singletonList(
                "org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc#openapiJson"
        );
        // @formatter:on

        log.debug("add excluded: {}", excluded);

        responseAdvisorProperties.addExcluded(excluded);
    }

}