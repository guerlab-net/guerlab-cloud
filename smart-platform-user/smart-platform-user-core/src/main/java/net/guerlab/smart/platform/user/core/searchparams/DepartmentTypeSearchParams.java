package net.guerlab.smart.platform.user.core.searchparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.smart.platform.commons.searchparams.OrderSearchParams;

/**
 * 部门类型搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("部门类型搜索参数")
public class DepartmentTypeSearchParams extends OrderSearchParams {

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
}
