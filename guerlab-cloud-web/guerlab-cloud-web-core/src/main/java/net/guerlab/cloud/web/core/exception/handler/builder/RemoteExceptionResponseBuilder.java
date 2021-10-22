package net.guerlab.cloud.web.core.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.RemoteException;

/**
 * 远端异常处理
 *
 * @author guer
 */
public class RemoteExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof RemoteException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        RemoteException exception = (RemoteException) e;
        String message = getMessage(e.getLocalizedMessage());
        Fail<Void> fail = new Fail<>(message);
        fail.setStackTraces(exception.getApplicationStackTraces());
        return fail;
    }
}
