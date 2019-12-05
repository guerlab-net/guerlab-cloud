package net.guerlab.smart.platform.commons.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * 可排序树形结构
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@Getter
@Setter
public abstract class BaseOrderTreeEntity<E extends BaseOrderTreeEntity<E>> extends BaseOrderEntity<E> {

    /**
     * 下级列表
     */
    @ApiModelProperty("下级列表")
    protected Collection<E> children;

    /**
     * 获取ID
     *
     * @return id
     */
    public abstract Long id();

    /**
     * 获取上级ID
     *
     * @return 上级id
     */
    public abstract Long parentId();
}
