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
package net.guerlab.cloud.web.webmvc.exception.handler;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.commons.exception.handler.ResponseBuilder;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.request.RequestHolder;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;

/**
 * 异常统一处理配置
 *
 * @author guer
 */
@Slf4j
public class WebMvcGlobalExceptionHandler extends GlobalExceptionHandler {

    /**
     * 初始化异常统一处理配置
     *
     * @param messageSource
     *         messageSource
     * @param stackTracesHandler
     *         堆栈处理
     * @param globalExceptionLogger
     *         全局异常处理日志记录器
     * @param builders
     *         异常信息构建者列表
     */
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
    public Fail<?> exceptionHandler(Exception e) {
        globalExceptionLogger.debug(e, RequestHolder.getRequestMethod(), RequestHolder.getRequestPath());
        return build(e);
    }

}
