package net.guerlab.cloud.web.core.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.web.result.Fail;

/**
 * ApplicationException异常处理
 *
 * @author guer
 */
public class ApplicationExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof ApplicationException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        ApplicationException exception = (ApplicationException) e;
        String message = getMessage(e.getLocalizedMessage());
        Fail<?> fail = new Fail<Void>(message, exception.getErrorCode());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }

    @Override
    public int getOrder() {
        return super.getOrder() + 10;
    }
}
