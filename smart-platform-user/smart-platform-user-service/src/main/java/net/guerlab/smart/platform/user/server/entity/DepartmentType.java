package net.guerlab.smart.platform.user.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门类型
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_department_type")
public class DepartmentType extends BaseOrderEntity<DepartmentType> implements DefaultConvertDTO<DepartmentTypeDTO> {

    public static final String DEFAULT_KEY = "";

    public static final String DEFAULT_NAME = "";

    /**
     * 部门类型关键字
     */
    @Id
    private String departmentTypeKey;

    /**
     * 部门类型名称
     */
    private String departmentTypeName;

    /**
     * 说明
     */
    private String remark;
}
