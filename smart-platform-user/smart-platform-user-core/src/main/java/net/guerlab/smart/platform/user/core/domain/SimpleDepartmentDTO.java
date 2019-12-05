package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderTreeEntity;

/**
 * 简单部门
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("简单部门")
public class SimpleDepartmentDTO extends BaseOrderTreeEntity<SimpleDepartmentDTO> {

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

    @Override
    public Long id() {
        return departmentId;
    }

    @Override
    public Long parentId() {
        return parentId;
    }
}
