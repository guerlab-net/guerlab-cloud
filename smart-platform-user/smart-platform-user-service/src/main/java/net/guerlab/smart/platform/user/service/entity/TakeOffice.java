package net.guerlab.smart.platform.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.guerlab.smart.platform.user.core.domain.TakeOfficeDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 任职信息
 *
 * @author guer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_take_office")
public class TakeOffice implements DefaultConvertDTO<TakeOfficeDTO> {

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
     * 职位ID
     */
    @Id
    private Long positionId;
}
