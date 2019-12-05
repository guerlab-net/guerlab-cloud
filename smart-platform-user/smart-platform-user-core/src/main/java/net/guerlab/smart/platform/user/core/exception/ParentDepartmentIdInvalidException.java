package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 上级部门ID无效
 *
 * @author guer
 */
public class ParentDepartmentIdInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.parentDepartmentIdInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
