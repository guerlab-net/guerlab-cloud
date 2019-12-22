package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 职务名称无效
 *
 * @author guer
 */
public class DutyNameInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.dutyNameInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
