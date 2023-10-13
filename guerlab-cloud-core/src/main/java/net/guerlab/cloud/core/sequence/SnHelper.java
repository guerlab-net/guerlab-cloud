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

package net.guerlab.cloud.core.sequence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.guerlab.commons.random.RandomUtil;

/**
 * 序列号助手.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class SnHelper {

	private static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	private SnHelper() {
	}

	/**
	 * 创建序列号.
	 *
	 * @return 序列号
	 */
	@SuppressWarnings("unused")
	public static String createSn() {
		return LocalDateTime.now().format(SIMPLE_DATE_FORMAT) + (RandomUtil.nextInt(900) + 100);
	}

}
