package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 该用户不能删除
 *
 * @author guer
 */
public class UserCannotDeleteException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.userCannotDelete";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
