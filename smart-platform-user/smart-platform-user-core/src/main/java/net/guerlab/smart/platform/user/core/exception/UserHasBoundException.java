package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 用户已绑定
 *
 * @author guer
 */
public class UserHasBoundException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.userHasBound";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
