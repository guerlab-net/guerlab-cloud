package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 职务权限
 *
 * @author guer
 */
@Data
@ApiModel("职务权限")
public class DutyPermissionDTO {

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 职务ID
     */
    @ApiModelProperty("职务ID")
    private Long dutyId;

    /**
     * 权限关键字
     */
    @ApiModelProperty("权限关键字")
    private String permissionKey;
}
