/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

/**
 * 时间单位语言包key.
 *
 * @author guer
 */
public enum TimeUnitMessageKey {
	/**
	 * 纳秒.
	 */
	NANOSECONDS(TimeUnit.NANOSECONDS, "time.nanoSeconds"),
	/**
	 * 微秒.
	 */
	MICROSECONDS(TimeUnit.MICROSECONDS, "time.microSeconds"),
	/**
	 * 毫秒.
	 */
	MILLISECONDS(TimeUnit.MILLISECONDS, "time.milliSeconds"),
	/**
	 * 秒.
	 */
	SECONDS(TimeUnit.SECONDS, "time.seconds"),
	/**
	 * 分.
	 */
	MINUTES(TimeUnit.MINUTES, "time.minutes"),
	/**
	 * 小时.
	 */
	HOURS(TimeUnit.HOURS, "time.hours"),
	/**
	 * 天.
	 */
	DAYS(TimeUnit.DAYS, "time.days");

	private static final EnumMap<TimeUnit, String> KEY_MAP = new EnumMap<>(TimeUnit.class);

	static {
		for (TimeUnitMessageKey value : TimeUnitMessageKey.values()) {
			KEY_MAP.put(value.timeUnit, value.key);
		}
	}

	private final TimeUnit timeUnit;

	/**
	 * 语言包key.
	 */
	@Getter
	private final String key;

	TimeUnitMessageKey(TimeUnit timeUnit, String key) {
		this.timeUnit = timeUnit;
		this.key = key;
	}

	/**
	 * 根据时间单位获取语言包key.
	 *
	 * @param timeUnit 时间单位
	 * @return 语言包key
	 */
	public static String getKey(TimeUnit timeUnit) {
		return KEY_MAP.get(timeUnit);
	}

}
