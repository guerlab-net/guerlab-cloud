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
