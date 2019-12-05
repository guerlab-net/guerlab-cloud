package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 手机号无效
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class PhoneNoInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.phoneNoInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
