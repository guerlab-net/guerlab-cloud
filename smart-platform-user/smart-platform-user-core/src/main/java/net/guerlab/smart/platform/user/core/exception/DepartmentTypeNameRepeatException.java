package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 部门类型名称重复
 *
 * @author guer
 */
public class DepartmentTypeNameRepeatException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.departmentTypeNameRepeat";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
