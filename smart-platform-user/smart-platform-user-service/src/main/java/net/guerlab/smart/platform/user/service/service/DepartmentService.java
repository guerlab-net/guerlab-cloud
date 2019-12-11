package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.service.entity.Department;
import net.guerlab.smart.platform.user.service.entity.DepartmentType;

/**
 * 部门服务
 *
 * @author guer
 */
public interface DepartmentService extends BaseService<Department, Long> {

    /**
     * 设置主管领导
     *
     * @param departmentId
     *         部门id
     * @param userId
     *         用户id
     */
    void setDirectorUser(Long departmentId, Long userId);

    /**
     * 设置分管领导
     *
     * @param departmentId
     *         部门id
     * @param userId
     *         用户id
     */
    void setChargeUser(Long departmentId, Long userId);

    /**
     * 移除主管领导
     *
     * @param departmentId
     *         部门id
     */
    void removeDirectorUser(Long departmentId);

    /**
     * 移除分管领导
     *
     * @param departmentId
     *         部门id
     */
    void removeChargeUser(Long departmentId);

    /**
     * 根据部门类型更新数据
     *
     * @param departmentType
     *         部门类型
     */
    void updateByDepartmentType(DepartmentType departmentType);

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<Department> getEntityClass() {
        return Department.class;
    }
}
