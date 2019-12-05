package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单权限
 *
 * @author guer
 */
@Data
@ApiModel("菜单权限")
public class MenuPermissionDTO {

    /**
     * 权限关键字
     */
    @ApiModelProperty("权限关键字")
    private String permissionKey;

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Long menuId;
}
