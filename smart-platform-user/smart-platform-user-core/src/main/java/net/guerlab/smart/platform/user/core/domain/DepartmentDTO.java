package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderTreeEntity;

import java.time.LocalDateTime;

/**
 * 部门
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("部门")
public class DepartmentDTO extends BaseOrderTreeEntity<DepartmentDTO> {

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /**
     * 上级部门ID
     */
    @ApiModelProperty("上级部门ID")
    private Long parentId;

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

    /**
     * 主管领导用户id
     */
    @ApiModelProperty("主管领导用户id")
    private Long directorUserId;

    /**
     * 主管领导用户姓名
     */
    @ApiModelProperty("主管领导用户姓名")
    private String directorUserName;

    /**
     * 分管领导用户id
     */
    @ApiModelProperty("分管领导用户id")
    private Long chargeUserId;

    /**
     * 分管领导用户姓名
     */
    @ApiModelProperty("分管领导用户姓名")
    private String chargeUserName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @Override
    public Long id() {
        return departmentId;
    }

    @Override
    public Long parentId() {
        return parentId;
    }
}
