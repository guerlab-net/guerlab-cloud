package net.guerlab.smart.platform.commons.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 可排序实体
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@Getter
@Setter
public abstract class BaseOrderEntity<E extends BaseOrderEntity<?>> extends BaseEntity implements IOrderEntity<E> {

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    @Column(name = "orderNum", nullable = false)
    protected Integer orderNum;
}
