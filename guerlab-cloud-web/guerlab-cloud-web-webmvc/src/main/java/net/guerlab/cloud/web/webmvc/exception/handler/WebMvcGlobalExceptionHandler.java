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

import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.cloud.web.webmvc.exception.NoHandlerFoundExceptionInfo;
import net.guerlab.web.result.Fail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理配置
 *
 * @author guer
 */
public class WebMvcGlobalExceptionHandler extends GlobalExceptionHandler {

    /**
     * NoHandlerFoundException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Fail<Void> noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
        debug(request, e);
        return handler0(new NoHandlerFoundExceptionInfo(e));
    }

}
