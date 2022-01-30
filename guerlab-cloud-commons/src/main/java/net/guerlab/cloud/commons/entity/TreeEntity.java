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
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * 树形结构对象
 *
 * @param <E>
 *         对象类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
@Data
@Schema(name = "TreeEntity", description = "树形结构对象")
public class TreeEntity<E, PK> {

    /**
     * 对象ID
     */
    @Schema(description = "对象ID")
    private PK id;

    /**
     * 上级ID
     */
    @Schema(description = "上级ID")
    private PK parentId;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private String label;

    /**
     * 目标对象
     */
    @Schema(description = "目标对象")
    private E object;

    /**
     * 下级列表
     */
    @Nullable
    @Schema(description = "下级列表")
    protected Collection<TreeEntity<E, PK>> children;
}
