package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;

/**
 * 部门类型服务接口
 *
 * @author guer
 */
public interface DepartmentTypeApi {

    /**
     * 根据部门类型关键字查询部门类型
     *
     * @param departmentTypeKey
     *         部门类型关键字
     * @return 部门类型
     */
    DepartmentTypeDTO findOne(String departmentTypeKey);

    /**
     * 根据搜索参数查询部门类型列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门类型列表
     */
    ListObject<DepartmentTypeDTO> findList(DepartmentTypeSearchParams searchParams);

    /**
     * 根据搜索参数查询部门类型列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门类型列表
     */
    List<DepartmentTypeDTO> findAll(DepartmentTypeSearchParams searchParams);
}
