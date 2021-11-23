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

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.RequestParamsError;
import net.guerlab.cloud.web.core.exception.handler.AbstractRequestParamsErrorResponseBuilder;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * MethodArgumentNotValidException异常处理
 *
 * @author guer
 */
public class MethodArgumentNotValidExceptionResponseBuilder extends AbstractRequestParamsErrorResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof MethodArgumentNotValidException;
    }

    @Override
    public Fail<Collection<String>> build(Throwable e) {
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        BindingResult bindingResult = exception.getBindingResult();

        Collection<String> displayMessageList = bindingResult.getAllErrors().stream()
                .map(this::getMethodArgumentNotValidExceptionDisplayMessage).collect(Collectors.toList());

        return build0(new RequestParamsError(exception, displayMessageList));
    }

    private String getMethodArgumentNotValidExceptionDisplayMessage(ObjectError error) {
        String defaultMessage = error.getDefaultMessage();

        if (defaultMessage == null) {
            return error.getObjectName() + error.getDefaultMessage();
        }

        try {
            return messageSource.getMessage(defaultMessage, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return error.getObjectName() + error.getDefaultMessage();
        }
    }
}
