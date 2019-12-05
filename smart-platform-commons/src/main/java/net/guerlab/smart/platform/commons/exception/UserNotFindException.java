package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 未查询到用户
 *
 * @author guer
 */
public class UserNotFindException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.userNotFind";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
