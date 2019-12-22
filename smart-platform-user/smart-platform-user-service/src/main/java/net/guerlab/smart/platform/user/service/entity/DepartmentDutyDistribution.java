package net.guerlab.smart.platform.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门职务分配
 *
 * @author guer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_department_duty_distribution")
public class DepartmentDutyDistribution {

    /**
     * 部门ID
     */
    @Id
    private Long departmentId;

    /**
     * 职务ID
     */
    @Id
    private Long dutyId;
}
