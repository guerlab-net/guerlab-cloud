package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 密码错误异常
 *
 * @author guer
 */
public class PasswordErrorException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.passwordError";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
