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

package net.guerlab.cloud.core.util;

import java.util.Objects;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/**
 * 环境工具类.
 *
 * @author guer
 */
public final class EnvUtils {

	private EnvUtils() {
	}

	/**
	 * 获取环境变量.
	 * 当变量不存在或变量值为空字符串时，返回默认值
	 *
	 * @param key          环境变量key
	 * @param defaultValue 默认值
	 * @return 环境变量
	 */
	public static String getEnv(String key, String defaultValue) {
		Objects.requireNonNull(defaultValue);
		String val = getEnv(key);
		return val == null ? defaultValue : val;
	}

	/**
	 * 获取环境变量.
	 * 当变量不存在或变量值为空字符串时，返回null
	 *
	 * @param key 环境变量key
	 * @return 环境变量
	 */
	@Nullable
	public static String getEnv(String key) {
		String val = System.getenv(key);

		try {
			val = System.getenv(key);
		}
		catch (Exception ignored) {
		}

		return StringUtils.trimToNull(val);
	}
}
