package net.guerlab.smart.platform.user.service.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 职务
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_duty")
public class Duty extends BaseOrderEntity<Duty> implements DefaultConvertDTO<DutyDTO> {

    /**
     * 职务ID
     */
    @Id
    private Long dutyId;

    /**
     * 职务名称
     */
    private String dutyName;

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
