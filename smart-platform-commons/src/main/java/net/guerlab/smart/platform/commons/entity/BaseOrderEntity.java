package net.guerlab.smart.platform.commons.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 可排序实体
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@Getter
@Setter
@Schema(name = "BaseOrderEntity", description = "可排序实体")
public abstract class BaseOrderEntity<E extends BaseOrderEntity<?>> extends BaseEntity implements IOrderEntity<E> {

    /**
     * 排序值
     */
    @Schema(description = "排序值")
    protected Integer orderNum;
}
