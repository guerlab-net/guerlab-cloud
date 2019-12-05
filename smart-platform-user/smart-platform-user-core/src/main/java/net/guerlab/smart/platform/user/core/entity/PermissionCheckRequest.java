package net.guerlab.smart.platform.user.core.entity;

import lombok.Data;

import java.util.List;

/**
 * 权限检查请求
 *
 * @author guer
 */
@Data
public class PermissionCheckRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 权限Key列表
     */
    private List<String> permissionKeys;
}
