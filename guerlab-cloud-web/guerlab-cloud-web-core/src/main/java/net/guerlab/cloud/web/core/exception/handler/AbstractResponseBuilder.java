package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.web.core.exception.AbstractI18nInfo;
import net.guerlab.web.result.Fail;
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

    protected Fail<?> buildByI18nInfo(AbstractI18nInfo i18nInfo, Throwable e) {
        String message = i18nInfo.getMessage(messageSource);
        Fail<?> fail = new Fail<>(message, i18nInfo.getErrorCode());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
