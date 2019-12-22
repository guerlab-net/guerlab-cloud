package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.user.core.searchparams.DutyPermissionSearchParams;
import net.guerlab.smart.platform.user.service.entity.DutyPermission;
import net.guerlab.smart.platform.user.service.entity.Position;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 职务权限服务
 *
 * @author guer
 */
public interface DutyPermissionService {

    /**
     * 根据搜索参数查询职务权限列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职务权限列表
     */
    Collection<DutyPermission> findList(DutyPermissionSearchParams searchParams);

    /**
     * 根据搜索参数查询权限关键字列表
     *
     * @param searchParams
     *         搜索参数
     * @return 权限关键字列表
     */
    default Collection<String> findPermissionKeyList(DutyPermissionSearchParams searchParams) {
        return findList(searchParams).stream().map(DutyPermission::getPermissionKey).collect(Collectors.toList());
    }

    /**
     * 根据任职信息列表查询职务权限列表
     *
     * @param list
     *         任职信息列表
     * @return 职务权限列表
     */
    Collection<DutyPermission> findList(Collection<Position> list);

    /**
     * 根据任职信息列表查询权限关键字列表
     *
     * @param list
     *         任职信息列表
     * @return 权限关键字列表
     */
    default Collection<String> findPermissionKeyList(Collection<Position> list) {
        return findList(list).stream().map(DutyPermission::getPermissionKey).collect(Collectors.toList());
    }

    /**
     * 保存职务权限列表
     *
     * @param departmentId
     *         部门id
     * @param dutyId
     *         职务id
     * @param keys
     *         权限关键字列表
     */
    void save(Long departmentId, Long dutyId, Collection<String> keys);

    /**
     * 删除职务权限列表
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(DutyPermissionSearchParams searchParams);
}
