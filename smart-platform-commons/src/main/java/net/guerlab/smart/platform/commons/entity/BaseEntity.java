package net.guerlab.smart.platform.commons.entity;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础实体
 *
 * @author guer
 */
@Setter
@Getter
@Schema(name = "BaseEntity", description = "基础实体")
public abstract class BaseEntity {

    /**
     * 乐观锁版本
     */
    @Version
    @Schema(description = "乐观锁版本")
    protected Long version;
}
