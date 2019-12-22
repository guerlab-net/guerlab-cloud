package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 部门无职务分配权限
 *
 * @author guer
 */
public class DepartmentNotHasDutyDistributionException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.departmentNotHasDutyDistribution";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
