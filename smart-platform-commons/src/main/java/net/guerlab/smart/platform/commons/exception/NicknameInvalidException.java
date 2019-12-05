package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 昵称无效
 *
 * @author guer
 */
public class NicknameInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.nicknameInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
