package net.guerlab.smart.platform.user.server.service;

import net.guerlab.smart.platform.user.core.searchparams.PositionPermissionSearchParams;
import net.guerlab.smart.platform.user.server.entity.PositionPermission;
import net.guerlab.smart.platform.user.server.entity.TakeOffice;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 职位权限服务
 *
 * @author guer
 */
public interface PositionPermissionService {

    /**
     * 根据搜索参数查询职位权限列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位权限列表
     */
    Collection<PositionPermission> findList(PositionPermissionSearchParams searchParams);

    /**
     * 根据搜索参数查询权限关键字列表
     *
     * @param searchParams
     *         搜索参数
     * @return 权限关键字列表
     */
    default Collection<String> findPermissionKeyList(PositionPermissionSearchParams searchParams) {
        return findList(searchParams).stream().map(PositionPermission::getPermissionKey).collect(Collectors.toList());
    }

    /**
     * 根据任职信息列表查询职位权限列表
     *
     * @param list
     *         任职信息列表
     * @return 职位权限列表
     */
    Collection<PositionPermission> findList(Collection<TakeOffice> list);

    /**
     * 根据任职信息列表查询权限关键字列表
     *
     * @param list
     *         任职信息列表
     * @return 权限关键字列表
     */
    default Collection<String> findPermissionKeyList(Collection<TakeOffice> list) {
        return findList(list).stream().map(PositionPermission::getPermissionKey).collect(Collectors.toList());
    }

    /**
     * 保存职位权限列表
     *
     * @param departmentId
     *         部门id
     * @param positionId
     *         职位id
     * @param keys
     *         权限关键字列表
     */
    void save(Long departmentId, Long positionId, Collection<String> keys);

    /**
     * 删除职位权限列表
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(PositionPermissionSearchParams searchParams);
}
