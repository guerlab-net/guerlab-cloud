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
package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.web.core.exception.RequestParamsError;
import net.guerlab.web.result.Fail;

import java.util.Collection;

/**
 * 抽象请求参数错误响应构建
 *
 * @author guer
 */
public abstract class AbstractRequestParamsErrorResponseBuilder extends AbstractResponseBuilder {

    protected Fail<Collection<String>> build0(RequestParamsError e) {
        String message = getMessage(e.getLocalizedMessage());
        Fail<Collection<String>> fail = new Fail<>(message, e.getErrorCode());
        fail.setData(e.getErrors());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }
}