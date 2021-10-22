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
    public Fail<?> build(Throwable e) {
        AbstractI18nApplicationException exception = (AbstractI18nApplicationException) e;
        String message = exception.getMessage(messageSource);
        Fail<?> fail = new Fail<>(message, exception.getErrorCode());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }
}
