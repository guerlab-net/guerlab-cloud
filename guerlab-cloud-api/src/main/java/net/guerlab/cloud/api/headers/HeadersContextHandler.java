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

package net.guerlab.cloud.api.headers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.lang.Nullable;

/**
 * 请求头上下文处理器.
 *
 * @author guer
 */
public final class HeadersContextHandler {

	private static final InheritableThreadLocal<Map<String, String>> THREAD_LOCAL = new InheritableThreadLocal<>() {

		@Override
		protected Map<String, String> initialValue() {
			return new HashMap<>(16);
		}
	};

	private HeadersContextHandler() {

	}

	/**
	 * 设置内容.
	 *
	 * @param key
	 *         key
	 * @param value
	 *         内容
	 */
	@SuppressWarnings("SameParameterValue")
	public static void set(String key, String value) {
		THREAD_LOCAL.get().put(key, value);
	}

	/**
	 * 获取内容.
	 *
	 * @param key
	 *         key
	 * @return 内容
	 */
	@SuppressWarnings({"SameParameterValue"})
	@Nullable
	public static String get(String key) {
		return THREAD_LOCAL.get().get(key);
	}

	/**
	 * 对内容遍历.
	 *
	 * @param action
	 *         遍历处理
	 */
	public static void forEach(BiConsumer<? super String, ? super String> action) {
		THREAD_LOCAL.get().forEach(action);
	}

	/**
	 * 清除当前内容.
	 */
	public static void clean() {
		THREAD_LOCAL.remove();
	}

}
