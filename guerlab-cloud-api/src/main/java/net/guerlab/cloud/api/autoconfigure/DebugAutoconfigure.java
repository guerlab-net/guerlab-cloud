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
package net.guerlab.cloud.api.autoconfigure;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.api.debug.DebugProxyRequestInterceptor;
import net.guerlab.cloud.api.properties.DebugProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 测试环境配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DebugProperties.class)
@ConditionalOnProperty(prefix = DebugProperties.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
public class DebugAutoconfigure {

    @Bean
    public RequestInterceptor debugProxyRequestInterceptor(DebugProperties properties) {
        return new DebugProxyRequestInterceptor(properties);
    }

}
