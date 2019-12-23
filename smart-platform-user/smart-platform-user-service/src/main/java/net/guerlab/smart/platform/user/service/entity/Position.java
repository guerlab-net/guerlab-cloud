package net.guerlab.smart.platform.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.guerlab.smart.platform.user.core.domain.IPosition;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职位
 *
 * @author guer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_position")
public class Position implements DefaultConvertDTO<PositionDTO>, IPosition {

    /**
     * 用户id
     */
    @Id
    private Long userId;

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
