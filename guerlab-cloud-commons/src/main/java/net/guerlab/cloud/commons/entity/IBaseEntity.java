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
import java.time.LocalDateTime;

import javax.annotation.Nullable;

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

	/**
	 * 获取创建时间.
	 *
	 * @return 创建时间
	 */
	LocalDateTime createdTime();

	/**
	 * 设置创建时间.
	 *
	 * @param createdTime 创建时间
	 */
	void createdTime(LocalDateTime createdTime);

	/**
	 * 获取最后修改时间.
	 *
	 * @return 最后修改时间
	 */
	LocalDateTime lastUpdatedTime();

	/**
	 * 设置最后修改时间.
	 *
	 * @param lastUpdatedTime 最后修改时间
	 */
	void lastUpdatedTime(LocalDateTime lastUpdatedTime);

	/**
	 * 获取创建人.
	 *
	 * @return 创建人
	 */
	String createdBy();

	/**
	 * 设置创建人.
	 *
	 * @param createdBy 创建人
	 */
	void createdBy(String createdBy);

	/**
	 * 获取修改人.
	 *
	 * @return 修改人
	 */
	String modifiedBy();

	/**
	 * 设置修改人.
	 *
	 * @param modifiedBy 修改人
	 */
	void modifiedBy(String modifiedBy);
}
