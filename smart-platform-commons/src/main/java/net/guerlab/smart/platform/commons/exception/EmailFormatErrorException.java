package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 邮箱格式错误
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class EmailFormatErrorException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.emailFormatError";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
