package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 系统职务不能操作
 *
 * @author guer
 */
public class SystemDutyCannotOperationException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.systemDutyCannotOperation";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
