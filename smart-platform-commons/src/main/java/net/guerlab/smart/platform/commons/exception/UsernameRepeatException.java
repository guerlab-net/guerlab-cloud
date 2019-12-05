package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 用户名重复
 *
 * @author guer
 */
public class UsernameRepeatException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.usernameRepeat";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
