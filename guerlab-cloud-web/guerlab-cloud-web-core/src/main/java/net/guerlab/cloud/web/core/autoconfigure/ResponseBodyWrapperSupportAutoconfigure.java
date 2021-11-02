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
package net.guerlab.cloud.web.core.autoconfigure;

import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 响应对象包装支持自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(ResponseAdvisorProperties.class)
public class ResponseBodyWrapperSupportAutoconfigure {

    /**
     * 构造响应对象包装支持
     *
     * @param properties
     *         http响应数据处理配置参数
     * @return 响应对象包装支持
     */
    @Bean
    public ResponseBodyWrapperSupport responseBodyWrapperSupport(ResponseAdvisorProperties properties) {
        return new ResponseBodyWrapperSupport(properties);
    }
}
