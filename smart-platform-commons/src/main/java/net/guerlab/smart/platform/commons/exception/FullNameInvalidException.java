package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 姓名无效
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class FullNameInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.fullNameInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
