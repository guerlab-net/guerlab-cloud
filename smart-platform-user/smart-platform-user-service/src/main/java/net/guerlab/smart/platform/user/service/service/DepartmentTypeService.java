package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
import net.guerlab.smart.platform.user.service.entity.DepartmentType;

/**
 * 部门类型服务
 *
 * @author guer
 */
public interface DepartmentTypeService extends BaseService<DepartmentType, String> {

    /**
     * 根据部门类型名称查询部门类型
     *
     * @param departmentTypeName
     *         部门类型名称
     * @return 部门类型
     */
    default DepartmentType selectByDepartmentTypeName(String departmentTypeName) {
        DepartmentTypeSearchParams searchParams = new DepartmentTypeSearchParams();
        searchParams.setDepartmentTypeName(departmentTypeName);

        return selectOne(searchParams);
    }

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<DepartmentType> getEntityClass() {
        return DepartmentType.class;
    }
}
