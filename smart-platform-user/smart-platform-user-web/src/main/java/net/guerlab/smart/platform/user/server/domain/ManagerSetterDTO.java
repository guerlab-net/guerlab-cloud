package net.guerlab.smart.platform.user.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门管理设置
 *
 * @author guer
 */
@Data
@ApiModel("部门管理设置")
public class ManagerSetterDTO {

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
}
