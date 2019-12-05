package net.guerlab.smart.platform.commons.entity;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;

/**
 * 实体
 *
 * @author guer
 */
@Setter
@Getter
public abstract class BaseEntity {

    /**
     * 乐观锁版本
     */
    @Version
    @Column(name = "version", insertable = false)
    protected Long version;
}
