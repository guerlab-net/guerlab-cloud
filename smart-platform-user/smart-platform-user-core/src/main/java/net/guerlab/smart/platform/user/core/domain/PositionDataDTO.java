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
public class PositionDataDTO {

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
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /**
     * 职务名称
     */
    @ApiModelProperty("职务名称")
    private String dutyName;
}
