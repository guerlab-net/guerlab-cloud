package net.guerlab.smart.platform.commons.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "下级列表")
    @TableField(exist = false)
    protected Collection<E> children;
}
