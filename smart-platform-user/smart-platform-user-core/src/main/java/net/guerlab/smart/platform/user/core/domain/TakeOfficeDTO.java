package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任职信息
 *
 * @author guer
 */
@Data
@ApiModel("任职信息")
public class TakeOfficeDTO {

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
     * 职位ID
     */
    @ApiModelProperty("职位ID")
    private Long positionId;

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
     * 职位
     */
    @ApiModelProperty("职位")
    private PositionDTO position;
}
