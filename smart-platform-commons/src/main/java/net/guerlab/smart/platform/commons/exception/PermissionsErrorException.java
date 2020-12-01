package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 权限错误
 *
 * @author guer
 */
public class PermissionsErrorException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.permissionsError";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    protected Object[] getArgs() {
        return new Object[0];
    }
}
