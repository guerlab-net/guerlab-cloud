package net.guerlab.smart.platform.user.service.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 职位
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_position")
public class Position extends BaseOrderEntity<Position> implements DefaultConvertDTO<PositionDTO> {

    /**
     * 职位ID
     */
    @Id
    private Long positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 权限列表
     */
    @Transient
    private transient Set<Permission> permissions = new HashSet<>();

}
