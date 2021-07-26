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
package net.guerlab.cloud.auth.web.autoconfig;

import net.guerlab.cloud.auth.web.interceptor.RequestInfoHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求信息拦截器配置
 *
 * @author guer
 */
@Order
@Configuration
public class RequestInfoInterceptorAutoconfigure implements WebMvcConfigurer {

    private final RequestInfoHandlerInterceptor interceptor = new RequestInfoHandlerInterceptor();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).order(interceptor.getOrder());
    }
}
