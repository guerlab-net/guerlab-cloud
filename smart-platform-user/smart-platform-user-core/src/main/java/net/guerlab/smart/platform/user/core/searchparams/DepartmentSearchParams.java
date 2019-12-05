package net.guerlab.smart.platform.user.core.searchparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.smart.platform.commons.searchparams.OrderSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import javax.persistence.Column;
import java.util.Collection;

/**
 * 部门搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("部门搜索参数")
public class DepartmentSearchParams extends OrderSearchParams {

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 部门ID列表
     */
    @ApiModelProperty("部门ID列表")
    @Column(name = "departmentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> departmentIds;

    /**
     * 上级部门ID
     */
    @ApiModelProperty("上级部门ID")
    private Long parentId;

    /**
     * 上级部门ID列表
     */
    @ApiModelProperty("上级部门ID列表")
    @Column(name = "parentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> parentIds;

    /**
     * 部门类型关键字
     */
    @ApiModelProperty("部门类型关键字")
    private String departmentTypeKey;

    /**
     * 部门类型关键字列表
     */
    @ApiModelProperty("部门类型关键字列表")
    @Column(name = "departmentTypeKey")
    @SearchModel(SearchModelType.IN)
    private Collection<String> departmentTypeKeys;

    /**
     * 主管领导用户id
     */
    @ApiModelProperty("主管领导用户id")
    private Long directorUserId;

    /**
     * 分管领导用户id
     */
    @ApiModelProperty("分管领导用户id")
    private Long chargeUserId;
}
