package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * AccessToken无效异常
 *
 * @author guer
 */
public class AccessTokenInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.accessTokenInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    public int getErrorCode() {
        return 401;
    }
}
