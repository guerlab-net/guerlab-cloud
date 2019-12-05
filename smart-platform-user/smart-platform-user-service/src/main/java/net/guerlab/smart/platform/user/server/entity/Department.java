package net.guerlab.smart.platform.user.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 部门
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_department")
public class Department extends BaseOrderEntity<Department> implements DefaultConvertDTO<DepartmentDTO> {

    /**
     * 部门ID
     */
    @Id
    private Long departmentId;

    /**
     * 部门名称
     */
    @Column(name = "departmentName", nullable = false)
    private String departmentName;

    /**
     * 上级部门ID
     */
    @Column(name = "parentId", nullable = false)
    private Long parentId = Constants.DEFAULT_PARENT_ID;

    /**
     * 部门类型关键字
     */
    private String departmentTypeKey;

    /**
     * 部门类型名称
     */
    private String departmentTypeName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 主管领导用户id
     */
    @Column(name = "directorUserId", nullable = false)
    private Long directorUserId;

    /**
     * 主管领导用户姓名
     */
    private String directorUserName;

    /**
     * 分管领导用户id
     */
    @Column(name = "chargeUserId", nullable = false)
    private Long chargeUserId;

    /**
     * 分管领导用户姓名
     */
    private String chargeUserName;

    /**
     * 更新时间
     */
    @Column(name = "updateTime", nullable = false)
    private LocalDateTime updateTime;
}
