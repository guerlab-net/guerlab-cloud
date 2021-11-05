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
package net.guerlab.cloud.web.core.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;
import net.guerlab.web.result.Fail;

/**
 * AbstractI18nApplicationException异常处理
 *
 * @author guer
 */
public class AbstractI18nApplicationExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof AbstractI18nApplicationException;
    }

    @Override
    public Fail<Void> build(Throwable e) {
        AbstractI18nApplicationException exception = (AbstractI18nApplicationException) e;
        String message = exception.getMessage(messageSource);
        Fail<Void> fail = new Fail<>(message, exception.getErrorCode());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }
}
