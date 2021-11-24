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
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * 树形结构
 *
 * @param <E>
 *         对象类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
@Getter
@Setter
@Schema(name = "BaseTreeEntity", description = "树形结构")
public abstract class BaseTreeEntity<E extends BaseTreeEntity<E, PK>, PK> implements ITreeEntity<E, PK> {

    /**
     * 下级列表
     */
    @Schema(description = "下级列表")
    @TableField(exist = false)
    protected Collection<E> children;
}
