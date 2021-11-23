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

import net.guerlab.cloud.commons.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.core.result.RemoteException;

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
    public Fail<Void> build(Throwable e) {
        RemoteException exception = (RemoteException) e;
        String message = getMessage(e.getLocalizedMessage());
        Fail<Void> fail = new Fail<>(message);
        fail.setStackTraces(exception.getApplicationStackTraces());
        return fail;
    }
}
