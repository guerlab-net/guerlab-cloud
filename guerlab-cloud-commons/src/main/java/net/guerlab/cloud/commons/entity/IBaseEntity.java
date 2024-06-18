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

import java.io.Serializable;

import jakarta.annotation.Nullable;

/**
 * 基础实体接口.
 *
 * @author guer
 */
public interface IBaseEntity extends Serializable {

	/**
	 * 获取ID.
	 *
	 * @return 获取ID
	 */
	@Nullable
	Long id();

	/**
	 * 设置ID.
	 *
	 * @param id ID
	 */
	void id(@Nullable Long id);
}
