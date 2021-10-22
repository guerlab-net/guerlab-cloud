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
package net.guerlab.cloud.web.webmvc.exception.handler;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.exception.handler.ResponseBuilder;
import net.guerlab.cloud.web.core.exception.handler.StackTracesHandler;
import net.guerlab.web.result.Fail;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 异常统一处理配置
 *
 * @author guer
 */
@Slf4j
public class WebMvcGlobalExceptionHandler extends GlobalExceptionHandler {

    public WebMvcGlobalExceptionHandler(MessageSource messageSource, StackTracesHandler stackTracesHandler,
            GlobalExceptionLogger globalExceptionLogger, Collection<ResponseBuilder> builders) {
        super(messageSource, stackTracesHandler, globalExceptionLogger, builders);
    }

    /**
     * 异常处理
     *
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(Exception.class)
    public Fail<?> exceptionHandler(HttpServletRequest request, Exception e) {
        globalExceptionLogger.debug(e, request.getMethod(), parseRequestUri(request));
        return build(e);
    }

    private String parseRequestUri(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }

}
