package net.guerlab.smart.platform.commons.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * 树形结构
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public abstract class BaseTreeEntity<E extends BaseTreeEntity<E>> {

    /**
     * 下级列表
     */
    @ApiModelProperty("下级列表")
    protected Collection<E> children;
}
