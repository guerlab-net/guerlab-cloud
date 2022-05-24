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

package net.guerlab.cloud.commons.enums;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import lombok.Getter;

import org.springframework.context.MessageSource;

import net.guerlab.cloud.core.util.SpringUtils;

/**
 * 统计周期.
 *
 * @author guer
 */
@Getter
public enum Cycle implements EnumDescriptionSupport {
	/**
	 * 年.
	 */
	YEAR("cycle.year") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//今年的1月1日
			return base.withDayOfYear(1);
		}
	},

	/**
	 * 半年.
	 */
	HALF_YEAR("cycle.half_year") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			if (base.getMonthValue() > 6) {
				//今年的7月1日
				return base.withMonth(7).withDayOfMonth(1);
			}
			else {
				//今年的1月1日
				return base.withMonth(1).withDayOfMonth(1);
			}
		}
	},

	/**
	 * 季.
	 */
	QUARTER("cycle.quarter") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//本季度第一个月的1日
			return base.withMonth(base.getMonth().firstMonthOfQuarter().getValue()).withDayOfMonth(1);
		}
	},

	/**
	 * 月.
	 */
	MONTH("cycle.month") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//本月1日
			return base.withDayOfMonth(1);
		}
	},

	/**
	 * 周.
	 */
	WEEK("cycle.week") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//本周一
			return base.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		}
	},

	/**
	 * 天.
	 */
	DAY("cycle.day") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//今天
			return base;
		}
	},

	/**
	 * 近30天.
	 */
	NEARLY_30_DAYS("cycle.nearly_30_days") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//30天前
			return base.minusDays(30);
		}
	},

	/**
	 * 近60天.
	 */
	NEARLY_60_DAYS("cycle.nearly_60_days") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//60天前
			return base.minusDays(60);
		}
	},

	/**
	 * 近90天.
	 */
	NEARLY_90_DAYS("cycle.nearly_90_days") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//90天前
			return base.minusDays(90);
		}
	},

	/**
	 * 近一月.
	 */
	NEARLY_ONE_MONTH("cycle.nearly_one_month") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//上个月
			return base.minusMonths(1);
		}
	},

	/**
	 * 近两个月.
	 */
	NEARLY_TWO_MONTH("cycle.nearly_two_month") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//上上个月
			return base.minusMonths(2);
		}
	},

	/**
	 * 近三个月.
	 */
	NEARLY_THREE_MONTH("cycle.nearly_three_month") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//今天
			return base.minusMonths(3);
		}
	},

	/**
	 * 近半年.
	 */
	NEARLY_HALF_YEAR("cycle.nearly_half_year") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//半年前
			return base.minusMonths(6);
		}
	},

	/**
	 * 近一年.
	 */
	NEARLY_ONE_YEAR("cycle.nearly_one_year") {
		@Override
		public LocalDateTime startTime(LocalDateTime base) {
			//一年前
			return base.minusYears(1);
		}
	};

	private final String key;

	Cycle(String key) {
		this.key = key;
	}

	@Override
	public String getDescription() {
		try {
			MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
			String description = messageSource.getMessage(key, null, name(), Locale.getDefault());
			return description == null ? name() : description;
		}
		catch (Exception ignored) {
			return name();
		}
	}

	/**
	 * 获取开始时间.
	 *
	 * @param base          基准时间
	 * @param startWithZero 是否从0点开始计算
	 * @return 开始时间
	 */
	public LocalDateTime startTime(LocalDateTime base, boolean startWithZero) {
		LocalDateTime start = startTime(base);
		if (startWithZero) {
			start = start.toLocalDate().atTime(LocalTime.MIN);
		}
		return start;
	}

	/**
	 * 获取开始时间.
	 *
	 * @param base 基准时间
	 * @return 开始时间
	 */
	public abstract LocalDateTime startTime(LocalDateTime base);

	/**
	 * 获取结束时间.
	 *
	 * @param base 基准时间
	 * @return 结束时间
	 */
	public LocalDateTime endTime(LocalDateTime base) {
		return base;
	}
}
