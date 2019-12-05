package net.guerlab.smart.platform.user.auth;

import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;

/**
 * 权限检查处理
 *
 * @author guer
 */
@FunctionalInterface
public interface PermissionCheckApi {

    /**
     * 权限检查
     *
     * @param request
     *         检查请求
     * @return 检查响应
     */
    PermissionCheckResponse accept(PermissionCheckRequest request);
}
