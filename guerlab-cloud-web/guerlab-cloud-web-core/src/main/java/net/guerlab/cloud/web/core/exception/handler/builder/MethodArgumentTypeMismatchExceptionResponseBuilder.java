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

import net.guerlab.cloud.web.core.exception.MethodArgumentTypeMismatchExceptionInfo;
import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.web.result.Fail;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * MethodArgumentTypeMismatchException异常处理
 *
 * @author guer
 */
public class MethodArgumentTypeMismatchExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof MethodArgumentTypeMismatchException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) e;
        return buildByI18nInfo(new MethodArgumentTypeMismatchExceptionInfo((exception)), e);
    }
}