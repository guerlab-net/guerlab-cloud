package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.user.service.entity.DepartmentParents;
import net.guerlab.smart.platform.user.service.searchparams.DepartmentParentsSearchParams;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 部门上下级关系服务
 *
 * @author guer
 */
public interface DepartmentParentsService {

    /**
     * 查询列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门上下级关系列表
     */
    Collection<DepartmentParents> findList(DepartmentParentsSearchParams searchParams);

    /**
     * 通过上级ID查询部门ID列表
     *
     * @param parentId
     *         上级ID
     * @return 部门ID列表
     */
    default Collection<Long> findDepartmentIdsByParentId(Long parentId) {
        DepartmentParentsSearchParams searchParams = new DepartmentParentsSearchParams();
        searchParams.setParentId(parentId);
        return findList(searchParams).stream().map(DepartmentParents::getDepartmentId).collect(Collectors.toList());
    }

    /**
     * 通过部门ID查询上级ID列表
     *
     * @param departmentId
     *         部门ID
     * @return 上级ID列表
     */
    default Collection<Long> findParentIdsByDepartmentId(Long departmentId) {
        DepartmentParentsSearchParams searchParams = new DepartmentParentsSearchParams();
        searchParams.setDepartmentId(departmentId);
        return findList(searchParams).stream().map(DepartmentParents::getParentId).collect(Collectors.toList());
    }

    /**
     * 保存部门上下级关系列表
     *
     * @param list
     *         部门上下级关系列表
     */
    void save(Collection<DepartmentParents> list);

    /**
     * 删除部门上下级关系列表
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(DepartmentParentsSearchParams searchParams);
}
