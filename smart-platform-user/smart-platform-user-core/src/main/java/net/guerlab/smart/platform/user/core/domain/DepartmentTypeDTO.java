package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;

/**
 * 部门类型
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("部门类型")
public class DepartmentTypeDTO extends BaseOrderEntity<DepartmentTypeDTO> {

    /**
     * 部门类型关键字
     */
    @ApiModelProperty("部门类型关键字")
    private String departmentTypeKey;

    /**
     * 部门类型名称
     */
    @ApiModelProperty("部门类型名称")
    private String departmentTypeName;

    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String remark;
}
