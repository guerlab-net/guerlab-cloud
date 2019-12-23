package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户职位信息
 *
 * @author guer
 */
@Data
@ApiModel("用户职位信息")
public class PositionDTO implements IPosition {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

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
     * 用户
     */
    @ApiModelProperty("用户")
    private UserDTO user;

    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private DepartmentDTO department;

    /**
     * 职务
     */
    @ApiModelProperty("职务")
    private DutyDTO duty;
}
