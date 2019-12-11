package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.user.service.entity.Department;

/**
 * 部门更新后置处理
 *
 * @author guer
 */
@FunctionalInterface
public interface AfterDepartmentUpdateHandler {

    /**
     * 部门更新后置处理
     *
     * @param department
     *         部门
     */
    void afterDepartmentUpdateHandler(Department department);
}
