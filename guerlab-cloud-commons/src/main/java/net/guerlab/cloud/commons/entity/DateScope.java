/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import net.guerlab.cloud.commons.enums.Cycle;
import net.guerlab.cloud.commons.exception.date.EndEarlierThanStartException;
import net.guerlab.cloud.commons.exception.date.EndIsNullException;
import net.guerlab.cloud.commons.exception.date.StartIsNullException;

/**
 * 日期范围.
 *
 * @author guer
 */
@Data
@Schema(name = "BaseEntity", description = "基础实体")
public class DateScope {

	/**
	 * 结束日期.
	 */
	@Schema(description = "结束日期")
	private LocalDate endDate;

	/**
	 * 日期偏移量.
	 */
	@Schema(description = "日期偏移量")
	private Long dateOffset;

	/**
	 * 开始日期.
	 */
	@Schema(description = "开始日期")
	private LocalDate startDate;

	/**
	 * 统计周期.
	 */
	@Schema(description = "统计周期")
	private Cycle cycle;

	/**
	 * 天数.
	 */
	@Schema(description = "天数")
	private Long days;

	/**
	 * 获取范围结果.
	 *
	 * @return 范围结果
	 */
	@JsonIgnore
	public RangeResult getRangeResult() {
		if (endDate == null && dateOffset == null) {
			throw new EndIsNullException();
		}
		else if (endDate == null) {
			endDate = LocalDate.now().plusDays(dateOffset);
		}

		if (startDate == null && cycle == null && days == null) {
			throw new StartIsNullException();
		}
		else if (startDate == null) {
			if (cycle != null) {
				startDate = cycle.startTime(endDate.atTime(LocalTime.MAX)).toLocalDate();
			}
			else {
				startDate = endDate.minusDays(days);
			}
		}

		if (endDate.isBefore(startDate)) {
			throw new EndEarlierThanStartException();
		}

		RangeResult rangeResult = new RangeResult();
		rangeResult.startDate = startDate;
		rangeResult.endDate = endDate;
		return rangeResult;
	}

	/**
	 * 范围结果.
	 *
	 * @author guer
	 */
	@Data
	public static final class RangeResult {
		/**
		 * 开始日期.
		 */
		@Schema(description = "开始日期")
		private LocalDate startDate;

		/**
		 * 结束日期.
		 */
		@Schema(description = "结束日期")
		private LocalDate endDate;
	}
}
