package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 系统职位不能操作
 *
 * @author guer
 */
public class SystemPositionCannotOperationException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.systemPositionCannotOperation";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
