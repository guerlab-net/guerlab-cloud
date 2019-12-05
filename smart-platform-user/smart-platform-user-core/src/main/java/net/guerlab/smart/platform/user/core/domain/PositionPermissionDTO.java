package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 职位权限
 *
 * @author guer
 */
@Data
@ApiModel("职位权限")
public class PositionPermissionDTO {

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 职位ID
     */
    @ApiModelProperty("职位ID")
    private Long positionId;

    /**
     * 权限关键字
     */
    @ApiModelProperty("权限关键字")
    private String permissionKey;
}
