package net.guerlab.smart.platform.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门职位分配
 *
 * @author guer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_department_position_distribution")
public class DepartmentPositionDistribution {

    /**
     * 部门ID
     */
    @Id
    private Long departmentId;

    /**
     * 职位ID
     */
    @Id
    private Long positionId;
}
