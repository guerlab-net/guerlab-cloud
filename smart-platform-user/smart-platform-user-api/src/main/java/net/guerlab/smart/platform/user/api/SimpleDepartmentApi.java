package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.SimpleDepartmentDTO;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;

/**
 * 简单部门服务接口
 *
 * @author guer
 */
public interface SimpleDepartmentApi {

    /**
     * 根据部门id查询部门
     *
     * @param departmentId
     *         部门id
     * @return 部门
     */
    SimpleDepartmentDTO findOne(Long departmentId);

    /**
     * 根据搜索参数查询部门列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门列表
     */
    ListObject<SimpleDepartmentDTO> findList(DepartmentSearchParams searchParams);

    /**
     * 根据搜索参数查询部门列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门列表
     */
    List<SimpleDepartmentDTO> findAll(DepartmentSearchParams searchParams);
}
