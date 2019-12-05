package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 用户名无效
 *
 * @author guer
 */
public class UsernameInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.usernameInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
