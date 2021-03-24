package net.guerlab.smart.platform.commons.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;

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
    protected Long version;
}
