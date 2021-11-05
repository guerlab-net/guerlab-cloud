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

import net.guerlab.cloud.web.core.autoconfigure.GlobalExceptionHandlerAutoConfigure;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.exception.handler.ResponseBuilder;
import net.guerlab.cloud.web.core.exception.handler.StackTracesHandler;
import net.guerlab.cloud.web.webmvc.exception.handler.WebMvcGlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * 异常统一处理配置自动配置
 *
 * @author guer
 */
@Configuration
@AutoConfigureAfter(GlobalExceptionHandlerAutoConfigure.class)
public class WebMvcGlobalExceptionHandlerAutoConfigure {

    private WebMvcGlobalExceptionHandlerAutoConfigure() {
    }

    /**
     * 异常统一处理配置
     *
     * @author guer
     */
    @SuppressWarnings("unused")
    @RestControllerAdvice
    public static class DefaultWebMvcGlobalExceptionHandler extends WebMvcGlobalExceptionHandler {

        public DefaultWebMvcGlobalExceptionHandler(MessageSource messageSource, StackTracesHandler stackTracesHandler,
                GlobalExceptionLogger globalExceptionLogger) {
            super(messageSource, stackTracesHandler, globalExceptionLogger,
                    ServiceLoader.load(ResponseBuilder.class).stream().map(ServiceLoader.Provider::get)
                            .collect(Collectors.toList()));
        }
    }
}
