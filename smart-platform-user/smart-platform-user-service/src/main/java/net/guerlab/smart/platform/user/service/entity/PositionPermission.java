package net.guerlab.smart.platform.user.service.entity;

import lombok.Data;
import net.guerlab.smart.platform.user.core.domain.PositionPermissionDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职位权限
 *
 * @author guer
 */
@Data
@Table(name = "user_position_permission")
public class PositionPermission implements DefaultConvertDTO<PositionPermissionDTO> {

    /**
     * 部门ID
     */
    @Id
    private Long departmentId;

    /**
     * 职位ID
     */
    @Id
    private Long positionId;

    /**
     * 权限关键字
     */
    @Id
    private String permissionKey;

    /**
     * 部门职位
     */
    private String departmentPosition;
}
