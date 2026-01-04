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

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;

import net.guerlab.cloud.core.Constants;

/**
 * 传递上下文.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class TransferContext {

	private TransferContext() {
	}

	/**
	 * 设置可传递内容.
	 *
	 * @param key   key
	 * @param value 内容
	 */
	@SuppressWarnings("SameParameterValue")
	public static void setTransfer(String key, String value) {
		String header = Constants.ALLOW_TRANSFER_HEADER_PREFIX + key;
		if (HeaderSafetyFilter.accept(header, value)) {
			ContextAttributesHolder.get().put(header, value);
		}
	}

	/**
	 * 获取可传递内容.
	 *
	 * @param key key
	 * @return 内容
	 */
	@Nullable
	public static String getTransfer(String key) {
		Object value = ContextAttributesHolder.get().get(Constants.ALLOW_TRANSFER_HEADER_PREFIX + key);
		if (value instanceof String v) {
			return v;
		}
		return null;
	}

	/**
	 * 获取所有可传递内容.
	 *
	 * @return 所有可传递内容
	 */
	public static Map<String, String> getAllTransfer() {
		Map<String, String> result = new HashMap<>();

		for (Map.Entry<Object, Object> entry : ContextAttributesHolder.get().entrySet()) {
			if ((entry.getKey() instanceof String key)
					&& (entry.getValue() instanceof String value)
					&& key.startsWith(Constants.ALLOW_TRANSFER_HEADER_PREFIX)
					&& HeaderSafetyFilter.accept(key, value)) {
				result.put(key, value);
			}
		}

		return result;
	}
}
