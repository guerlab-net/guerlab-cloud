package net.guerlab.smart.platform.user.core.entity;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;

/**
 * 权限检查响应
 *
 * @author guer
 */
@Data
public class PermissionCheckResponse {

    /**
     * 是否通过
     */
    private boolean accept;

    /**
     * 拥有的权限
     */
    private Collection<String> has = Collections.emptyList();

    /**
     * 不拥有的权限
     */
    private Collection<String> notHas = Collections.emptyList();
}
