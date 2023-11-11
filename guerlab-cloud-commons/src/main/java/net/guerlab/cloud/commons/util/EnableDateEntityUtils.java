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

package net.guerlab.cloud.commons.util;

import java.time.LocalDate;

import net.guerlab.cloud.commons.entity.EnableDateEntity;
import net.guerlab.cloud.commons.exception.StartDateIsAfterEndDateException;

import static net.guerlab.cloud.commons.Constants.MAX_DATE;

/**
 * 启用日期实体工具类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class EnableDateEntityUtils {

	private EnableDateEntityUtils() {

	}

	/**
	 * 值处理.
	 *
	 * @param entity 启用日期实体
	 */
	public static void valueHandler(EnableDateEntity entity) {
		LocalDate enableStartDate = entity.enableStartDate();
		LocalDate enableEndDate = entity.enableEndDate();
		if (enableStartDate == null) {
			enableStartDate = LocalDate.now();
		}
		if (enableEndDate == null || enableEndDate.isAfter(MAX_DATE)) {
			enableEndDate = MAX_DATE;
		}
		if (enableStartDate.isAfter(enableEndDate)) {
			throw new StartDateIsAfterEndDateException();
		}
		entity.enableStartDate(enableStartDate);
		entity.enableEndDate(enableEndDate);
	}

	/**
	 * 判断是否在日期范围内.
	 *
	 * @param entity 启用日期实体
	 * @param date   日期
	 * @return 是否在范围内
	 */
	public static boolean inDateRange(EnableDateEntity entity, LocalDate date) {
		LocalDate enableStartDate = entity.enableStartDate();
		LocalDate enableEndDate = entity.enableEndDate();

		if (enableStartDate != null && date.isBefore(enableStartDate)) {
			return false;
		}
		return enableEndDate == null || !date.isAfter(enableEndDate);
	}
}
