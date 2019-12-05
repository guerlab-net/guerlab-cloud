package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限
 *
 * @author guer
 */
@Data
@ApiModel("权限")
public class PermissionDTO {

    /**
     * 权限关键字
     */
    @ApiModelProperty("权限关键字")
    private String permissionKey;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String permissionName;

    /**
     * 权限说明
     */
    @ApiModelProperty("权限说明")
    private String remark;
}
