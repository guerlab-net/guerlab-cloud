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

import java.time.LocalDate;

import org.springframework.lang.Nullable;

/**
 * 启用日期实体.
 *
 * @author guer
 */
public interface EnableDateEntity {

	/**
	 * 获取启用开始日期.
	 *
	 * @return 启用开始日期
	 */
	@Nullable
	LocalDate getEnableStartDate();

	/**
	 * 设置启用开始日期.
	 *
	 * @param enableStartDate 启用开始日期
	 */
	void setEnableStartDate(@Nullable LocalDate enableStartDate);

	/**
	 * 获取启用结束日期.
	 *
	 * @return 启用结束日期
	 */
	@Nullable
	LocalDate getEnableEndDate();

	/**
	 * 设置启用结束日期.
	 *
	 * @param enableEndDate 启用结束日期
	 */
	void setEnableEndDate(@Nullable LocalDate enableEndDate);
}
