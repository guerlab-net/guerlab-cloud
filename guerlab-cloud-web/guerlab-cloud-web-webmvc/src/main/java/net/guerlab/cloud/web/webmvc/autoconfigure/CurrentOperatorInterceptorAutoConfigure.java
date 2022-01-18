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

package net.guerlab.cloud.web.webmvc.autoconfigure;

import net.guerlab.cloud.web.webmvc.interceptor.CurrentOperatorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 当前操作者信息处理请求拦截器自动配置
 *
 * @author guer
 */
@Configuration
public class CurrentOperatorInterceptorAutoConfigure implements WebMvcConfigurer {

    @Override
    public final void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CurrentOperatorInterceptor());
    }
}
