/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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
import jakarta.annotation.Nullable;

/**
 * 树节点.
 *
 * @param <K> 主键类型
 * @author guer
 */
@Schema(name = "TreeNode", description = "TreeNode")
public interface TreeNode<K> {

	/**
	 * 获取节点ID.
	 *
	 * @return 节点ID
	 */
	K nodeId();

	/**
	 * 获取上级ID.
	 *
	 * @return 上级id
	 */
	K parentId();

	/**
	 * 获取标签.
	 *
	 * @return 标签
	 */
	@SuppressWarnings("SameReturnValue")
	@Nullable
	String label();
}
