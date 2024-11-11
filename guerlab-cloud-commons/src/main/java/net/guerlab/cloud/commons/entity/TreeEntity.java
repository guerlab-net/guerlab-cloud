/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * 树形结构对象.
 *
 * @param <E> 对象类型
 * @param <K> 主键类型
 * @author guer
 */
@Data
@Schema(name = "TreeEntity", description = "树形结构对象")
public class TreeEntity<E, K> {
	/**
	 * 对象ID.
	 */
	@Schema(description = "对象ID")
	private K id;
	/**
	 * 上级ID.
	 */
	@Schema(description = "上级ID")
	private K parentId;
	/**
	 * 标签.
	 */
	@Schema(description = "标签")
	private String label;
	/**
	 * 目标对象.
	 */
	@Schema(description = "目标对象")
	private E object;
	/**
	 * 选中状态.
	 */
	@Schema(description = "选中状态")
	private boolean selected = false;
	/**
	 * 展开标记.
	 */
	@Schema(description = "展开标记")
	private boolean expanded = true;
	/**
	 * 禁用标记.
	 */
	@Schema(description = "禁用标记")
	private boolean disabled = false;
	/**
	 * 下级列表.
	 */
	@Nullable
	@Schema(description = "下级列表")
	protected Collection<TreeEntity<E, K>> children;
}
