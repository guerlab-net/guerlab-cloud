package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 部门无分管领导
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class DepartmentHasNotChargeUserException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.departmentHasNotChargeUser";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
