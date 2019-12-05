package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 不支持的登录方式
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class UnsupportedLoginModeException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.unsupportedLoginMode";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
