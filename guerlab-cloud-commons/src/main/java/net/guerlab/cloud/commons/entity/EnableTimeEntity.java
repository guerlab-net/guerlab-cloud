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

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;

/**
 * 启用时间实体.
 *
 * @author guer
 */
public interface EnableTimeEntity {

	/**
	 * 获取启用开始日期.
	 *
	 * @return 启用开始日期
	 */
	@Nullable
	LocalDateTime enableStartTime();

	/**
	 * 设置启用开始日期.
	 *
	 * @param enableStartTime 启用开始日期
	 */
	void enableStartTime(@Nullable LocalDateTime enableStartTime);

	/**
	 * 获取启用结束日期.
	 *
	 * @return 启用结束日期
	 */
	@Nullable
	LocalDateTime enableEndTime();

	/**
	 * 设置启用结束日期.
	 *
	 * @param enableEndTime 启用结束日期
	 */
	void enableEndTime(@Nullable LocalDateTime enableEndTime);
}
