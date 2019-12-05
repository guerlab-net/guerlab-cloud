package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 账户未启用
 *
 * @author guer
 */
public class UserUnableException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.userUnable";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
