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
package net.guerlab.cloud.web.webflux.autoconfigure;

import net.guerlab.cloud.web.webflux.exception.handler.WebFluxGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常统一处理配置自动配置
 *
 * @author guer
 */
@Configuration
public class WebFluxGlobalExceptionHandlerAutoConfigure {

    /**
     * 默认异常统一处理配置
     *
     * @author guer
     */
    @RestControllerAdvice
    public static class DefaultWebFluxGlobalExceptionHandler extends WebFluxGlobalExceptionHandler {}
}
