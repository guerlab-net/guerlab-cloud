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
package net.guerlab.cloud.web.webmvc.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.NoHandlerFoundExceptionInfo;
import net.guerlab.web.result.Fail;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * NoHandlerFoundException异常处理
 *
 * @author guer
 */
public class NoHandlerFoundExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof NoHandlerFoundException;
    }

    @Override
    public Fail<Void> build(Throwable e) {
        NoHandlerFoundException exception = (NoHandlerFoundException) e;
        return buildByI18nInfo(new NoHandlerFoundExceptionInfo(exception), e);
    }
}
