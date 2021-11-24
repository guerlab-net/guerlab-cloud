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

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

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
@Schema(name = "ITreeEntity", description = "树形结构")
public interface ITreeEntity<E extends ITreeEntity<E, PK>, PK> {

    /**
     * 获取下级列表
     *
     * @return 下级列表
     */
    @Nullable
    Collection<E> getChildren();

    /**
     * 设置下级列表
     *
     * @param children
     *         下级列表
     */
    void setChildren(Collection<E> children);

    /**
     * 获取ID
     *
     * @return id
     */
    PK id();

    /**
     * 获取上级ID
     *
     * @return 上级id
     */
    PK parentId();
}
