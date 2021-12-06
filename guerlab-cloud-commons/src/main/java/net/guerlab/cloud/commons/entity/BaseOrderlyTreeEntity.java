/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * 可排序树形结构
 *
 * @param <E>
 *         对象类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
@Schema(name = "BaseOrderlyTreeEntity", description = "可排序树形结构")
public abstract class BaseOrderlyTreeEntity<E extends BaseOrderlyTreeEntity<E, PK>, PK> extends BaseOrderlyEntity<E>
        implements IOrderlyTreeEntity<E, PK> {

    /**
     * 下级列表
     */
    @Schema(description = "下级列表")
    @TableField(exist = false)
    private Collection<E> children;

    @Nullable
    @Override
    public Collection<E> getChildren() {
        return children;
    }

    @Override
    public void setChildren(@Nullable Collection<E> children) {
        this.children = children;
    }
}
