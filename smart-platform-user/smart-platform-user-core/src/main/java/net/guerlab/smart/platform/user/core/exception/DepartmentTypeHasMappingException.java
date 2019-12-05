package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 部门类型已存在映射数据
 *
 * @author guer
 */
public class DepartmentTypeHasMappingException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.departmentTypeHasMapping";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
