package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 部门无主管领导
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class DepartmentHasNotDirectorUserException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.departmentHasNotDirectorUser";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
