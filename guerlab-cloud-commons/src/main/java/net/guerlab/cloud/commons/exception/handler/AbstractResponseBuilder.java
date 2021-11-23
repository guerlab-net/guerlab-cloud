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
package net.guerlab.cloud.commons.exception.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

import java.util.Locale;

/**
 * 抽象异常信息构建者
 *
 * @author guer
 */
public abstract class AbstractResponseBuilder implements ResponseBuilder {

    protected static final int DEFAULT_ORDER = 0;

    protected MessageSource messageSource;

    protected StackTracesHandler stackTracesHandler;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void setStackTracesHandler(StackTracesHandler stackTracesHandler) {
        this.stackTracesHandler = stackTracesHandler;
    }

    @Nullable
    protected String getMessage(String msg) {
        if (StringUtils.isBlank(msg)) {
            return msg;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(msg, null, msg, locale);
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
