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

package net.guerlab.commons.time.formater;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import net.guerlab.commons.time.FormatSupplier;

/**
 * 标准日期格式 "yyyy-MM-dd HH:mm:ss.[S{0, 9}]".
 *
 * @author guer
 */
public class StandardDate implements FormatSupplier {

	/**
	 * 实例.
	 */
	public static final StandardDate INSTANCE = new StandardDate();

	@Override
	public DateTimeFormatter get() {
		DateTimeFormatter isoLocalDateTime = new DateTimeFormatterBuilder().parseCaseInsensitive()
				.append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(DateTimeFormatter.ISO_LOCAL_TIME)
				.toFormatter();

		return new DateTimeFormatterBuilder().append(isoLocalDateTime).optionalStart().appendOffsetId().optionalStart()
				.appendLiteral('[').parseCaseSensitive().appendZoneRegionId().appendLiteral(']').toFormatter();
	}
}
