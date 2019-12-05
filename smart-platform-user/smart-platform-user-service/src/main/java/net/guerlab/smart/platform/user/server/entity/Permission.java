package net.guerlab.smart.platform.user.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseEntity;
import net.guerlab.smart.platform.user.core.domain.PermissionDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_permission")
public class Permission extends BaseEntity implements DefaultConvertDTO<PermissionDTO> {

    /**
     * 权限关键字
     */
    @Id
    private String permissionKey;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限说明
     */
    private String remark;
}
