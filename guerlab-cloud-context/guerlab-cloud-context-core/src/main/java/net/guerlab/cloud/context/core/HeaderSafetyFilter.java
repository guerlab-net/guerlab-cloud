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

package net.guerlab.cloud.context.core;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求头安全过滤.
 *
 * @author guer
 */
@Slf4j
public final class HeaderSafetyFilter {

	private static final Pattern ASSIGNED_AND_NOT_ISO_CONTROL_PATTERN = Pattern
			.compile("[\\p{IsAssigned}&&[^\\p{IsControl}]]*");

	private static final Predicate<String> ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE = (s) -> ASSIGNED_AND_NOT_ISO_CONTROL_PATTERN.matcher(s)
			.matches();

	private static final Pattern HEADER_VALUE_PATTERN = Pattern.compile("[\\p{IsAssigned}&&[[^\\p{IsControl}]||\\t]]*");

	private static final Predicate<String> HEADER_VALUE_PREDICATE = (s) -> HEADER_VALUE_PATTERN.matcher(s).matches();

	private HeaderSafetyFilter() {
	}

	/**
	 * 判断请求头是否合法.
	 *
	 * @param header header
	 * @param value  value
	 * @return 是否合法
	 */
	public static boolean accept(String header, String value) {
		if (!ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE.test(header)) {
			log.debug("header not match pattern: {}", header);
			return false;
		}
		if (!HEADER_VALUE_PREDICATE.test(value)) {
			log.debug("header value not match pattern: {} = {}", header, value);
			return false;
		}
		return true;
	}
}
