package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 简单用户
 *
 * @author guer
 */
@Data
@ApiModel("简单用户")
public class SimpleUserDTO {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;
}
