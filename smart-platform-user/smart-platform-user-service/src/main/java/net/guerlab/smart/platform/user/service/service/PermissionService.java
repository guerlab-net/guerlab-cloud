package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.service.entity.Permission;

/**
 * 权限服务
 *
 * @author guer
 */
public interface PermissionService extends BaseService<Permission, String> {

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<Permission> getEntityClass() {
        return Permission.class;
    }
}
