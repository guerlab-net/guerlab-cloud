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

package net.guerlab.commons.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ServiceLoader;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.guerlab.commons.time.formater.IsoDate;
import net.guerlab.commons.time.formater.StandardDate;

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.NANO_OF_DAY;

/**
 * 时间助手类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class TimeHelper {

	private static final TemporalQuery<Instant> INSTANT_TEMPORAL_QUERY;

	private static final ZoneOffset ZONE_OFFSET;

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeHelper.class);

	private static final List<FormatSupplier> FORMAT_SUPPLIERS = new ArrayList<>();

	static {
		ServiceLoader.load(FormatSupplier.class).forEach(TimeHelper::addFormat);

		ZoneId defaultZoneId = ZoneId.systemDefault();
		ZonedDateTime now = ZonedDateTime.now();
		ZoneRules rules = defaultZoneId.getRules();
		ZONE_OFFSET = rules.getOffset(now.toInstant());

		INSTANT_TEMPORAL_QUERY = temporal -> {
			if (temporal.isSupported(INSTANT_SECONDS)) {
				return Instant.ofEpochSecond(temporal.getLong(INSTANT_SECONDS));
			}
			else if (temporal.isSupported(EPOCH_DAY)) {
				return LocalDate.ofEpochDay(temporal.getLong(EPOCH_DAY)).atTime(LocalTime.MIN).toInstant(ZONE_OFFSET);
			}
			return null;
		};
	}

	private TimeHelper() {
	}

	/**
	 * 添加处理格式.
	 *
	 * @param formatSupplier 处理格式
	 */
	public static void addFormat(@Nullable final FormatSupplier formatSupplier) {
		if (formatSupplier != null) {
			FORMAT_SUPPLIERS.add(formatSupplier);
		}
	}

	/**
	 * 根据ZoneId获取ZoneOffset.
	 *
	 * @param zoneId ZoneId
	 * @return ZoneOffset
	 */
	public static ZoneOffset getZoneOffset(final ZoneId zoneId) {
		return zoneId.getRules().getOffset(Instant.now());
	}

	/**
	 * 日期类型对象格式化.
	 *
	 * @param temporal  TemporalAccessor
	 * @param formatter 格式化对象
	 * @return 格式化后的文本内容
	 */
	@Nullable
	public static String format(@Nullable final TemporalAccessor temporal, @Nullable final DateTimeFormatter formatter) {
		if (temporal == null || formatter == null) {
			return null;
		}
		return formatter.format(temporal);
	}

	/**
	 * 日期类型对象格式化.
	 *
	 * @param temporal TemporalAccessor
	 * @param pattern  模式
	 * @return 格式化后的文本内容
	 */
	@Nullable
	public static String format(@Nullable final TemporalAccessor temporal, final String pattern) {
		if (temporal == null || StringUtils.isBlank(pattern)) {
			return null;
		}

		return format(temporal, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 日期类型对象格式化成全时间格式.
	 *
	 * @param temporal TemporalAccessor
	 * @return 全时间格式的日期内容文本
	 */
	public static String format(final TemporalAccessor temporal) {
		return format(temporal, StandardDate.INSTANCE.get());
	}

	/**
	 * 日期类型对象格式化.
	 *
	 * @param date      日期类型对象
	 * @param formatter 格式化对象
	 * @return 格式化后的文本内容
	 */
	@Nullable
	public static String format(@Nullable final Date date, final DateTimeFormatter formatter) {
		if (date == null) {
			return null;
		}
		return format(date.toInstant().atZone(ZoneId.systemDefault()), formatter);
	}

	/**
	 * 日期类型对象格式化.
	 *
	 * @param date    日期类型对象
	 * @param pattern 模式
	 * @return 格式化后的文本内容
	 */
	@Nullable
	public static String format(@Nullable final Date date, final String pattern) {
		if (date == null || StringUtils.isBlank(pattern)) {
			return null;
		}

		return format(date, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 日期类型对象格式化成全时间格式.
	 *
	 * @param date 日期类型对象
	 * @return 全时间格式的日期内容文本
	 */
	@Nullable
	public static String format(final Date date) {
		return format(date, StandardDate.INSTANCE.get());
	}

	/**
	 * 日期类型对象格式化成只包含日期格式.
	 *
	 * @param date 日期类型对象
	 * @return 只包含日期格式的日期内容文本
	 */
	@Nullable
	public static String formatDate(final Date date) {
		return format(date, IsoDate.INSTANCE.get());
	}

	/**
	 * 日期类型对象格式化成只包含日期格式.
	 *
	 * @param temporal TemporalAccessor
	 * @return 只包含日期格式的日期内容文本
	 */
	public static String formatDate(final TemporalAccessor temporal) {
		return format(temporal, IsoDate.INSTANCE.get());
	}

	/**
	 * 通过时间字符串解析生成日期对象.
	 *
	 * @param string 时间字符串
	 * @return 日期对象
	 */
	@Nullable
	public static Date parse(final String string) {
		Instant instant = parseInstant(string);
		if (instant == null) {
			return null;
		}

		return Date.from(instant);
	}

	/**
	 * 通过字符串解析生成LocalDateTime对象.
	 *
	 * @param string 字符串
	 * @return LocalDateTime
	 */
	@Nullable
	public static LocalDateTime parseLocalDateTime(final String string) {
		Instant instant = parseInstant(string);
		return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	/**
	 * 通过字符串解析生成LocalDate对象.
	 *
	 * @param string 字符串
	 * @return LocalDate
	 */
	@Nullable
	public static LocalDate parseLocalDate(final String string) {
		TemporalQuery<LocalDate> query = temporal -> {
			if (temporal.isSupported(EPOCH_DAY)) {
				return LocalDate.ofEpochDay(temporal.getLong(EPOCH_DAY));
			}
			return null;
		};
		return parse(string, query);
	}

	/**
	 * 通过字符串解析生成LocalTime对象.
	 *
	 * @param string 字符串
	 * @return LocalTime
	 */
	@Nullable
	public static LocalTime parseLocalTime(final String string) {
		TemporalQuery<LocalTime> query = temporal -> {
			if (temporal.isSupported(NANO_OF_DAY)) {
				return LocalTime.ofNanoOfDay(temporal.getLong(NANO_OF_DAY));
			}
			return null;
		};
		return parse(string, query);
	}

	/**
	 * 通过字符串解析高精度时间戳.
	 *
	 * @param string 字符串
	 * @return 高精度时间戳
	 */
	@Nullable
	public static Instant parseInstant(final String string) {
		return parse(string, INSTANT_TEMPORAL_QUERY);
	}

	/**
	 * 通过时间字符串解析生成日期对象.
	 *
	 * @param string 时间字符串
	 * @return 日期对象
	 */
	@Nullable
	public static <R extends TemporalAccessor> R parse(final String string, final TemporalQuery<R> query) {
		String text = StringUtils.trimToNull(string);
		if (text == null) {
			return null;
		}

		TemporalAccessor temporal = numberParse(text);
		if (temporal == null) {
			temporal = textParse(text);
		}
		return temporal == null ? null : temporal.query(query);
	}

	@Nullable
	private static TemporalAccessor numberParse(final String text) {
		try {
			long timestamp = Long.parseLong(text);
			return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault());
		}
		catch (Exception e) {
			LOGGER.trace(e.getMessage(), e);
		}

		return null;
	}

	@Nullable
	private static TemporalAccessor textParse(final String text) {
		ZoneId zoneId = ZoneId.systemDefault();

		for (FormatSupplier format : FORMAT_SUPPLIERS) {
			try {
				return format.get().withZone(zoneId).parse(text);
			}
			catch (Exception e) {
				LOGGER.trace(e.getMessage(), e);
			}
		}
		return null;
	}
}
