package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;

import java.util.Collection;

/**
 * 权限检查服务
 *
 * @author guer
 */
@FunctionalInterface
public interface PermissionCheckService {

    /**
     * 权限检查
     *
     * @param userId
     *         用户id
     * @param permissionKeys
     *         权限关键字列表
     * @return 权限检查响应
     */
    PermissionCheckResponse acceptByPermissionKeys(Long userId, Collection<String> permissionKeys);
}
