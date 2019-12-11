package net.guerlab.smart.platform.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门上下级关系
 *
 * @author guer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_department_parents")
public class DepartmentParents {

    /**
     * 部门id
     */
    @Id
    private Long departmentId;

    /**
     * 上级id
     */
    @Id
    private Long parentId;
}
