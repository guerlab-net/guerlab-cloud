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
package net.guerlab.cloud.api.autoconfigure;

import net.guerlab.cloud.api.endpoints.FeignClientEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监控端点自动配置
 *
 * @author guer
 */
@Configuration
public class EndPointsAutoconfigure {

    /**
     * 构造FeignClient实例监控端点
     *
     * @return FeignClient实例监控端点
     */
    @Bean
    public FeignClientEndpoint feignClientEndpoint() {
        return new FeignClientEndpoint();
    }
}
