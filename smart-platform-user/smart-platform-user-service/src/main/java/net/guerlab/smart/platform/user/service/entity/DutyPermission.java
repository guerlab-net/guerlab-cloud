package net.guerlab.smart.platform.user.service.entity;

import lombok.Data;
import net.guerlab.smart.platform.user.core.domain.DutyPermissionDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职务权限
 *
 * @author guer
 */
@Data
@Table(name = "user_duty_permission")
public class DutyPermission implements DefaultConvertDTO<DutyPermissionDTO> {

    /**
     * 部门ID
     */
    @Id
    private Long departmentId;

    /**
     * 职务ID
     */
    @Id
    private Long dutyId;

    /**
     * 权限关键字
     */
    @Id
    private String permissionKey;

    /**
     * 部门职务
     */
    private String departmentDuty;
}
