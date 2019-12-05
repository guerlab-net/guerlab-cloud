package net.guerlab.smart.platform.user.server.service;

import net.guerlab.smart.platform.user.core.searchparams.MenuPermissionSearchParams;
import net.guerlab.smart.platform.user.server.entity.MenuPermission;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 菜单权限服务
 *
 * @author guer
 */
public interface MenuPermissionService {

    /**
     * 根据搜索参数查询菜单权限列表
     *
     * @param searchParams
     *         搜索参数
     * @return 菜单权限列表
     */
    Collection<MenuPermission> findList(MenuPermissionSearchParams searchParams);

    /**
     * 根据搜索参数查询菜单Id列表
     *
     * @param searchParams
     *         搜索参数
     * @return 菜单Id列表
     */
    default Collection<Long> findMenuIdList(MenuPermissionSearchParams searchParams) {
        return findList(searchParams).stream().map(MenuPermission::getMenuId).collect(Collectors.toList());
    }

    /**
     * 保存菜单权限列表
     *
     * @param permissionKey
     *         权限关键字
     * @param menuIds
     *         菜单id列表
     */
    void save(String permissionKey, Collection<Long> menuIds);

    /**
     * 删除菜单权限列表
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(MenuPermissionSearchParams searchParams);

    /**
     * 删除菜单权限列表
     *
     * @param permissionKey
     *         权限关键字
     */
    default void delete(String permissionKey) {
        if (StringUtils.isBlank(permissionKey)) {
            return;
        }
        MenuPermissionSearchParams searchParams = new MenuPermissionSearchParams();
        searchParams.setPermissionKey(permissionKey.trim());

        delete(searchParams);
    }

}
