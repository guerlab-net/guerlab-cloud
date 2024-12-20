/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.auth.context;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;

/**
 * 抽象上下文处理器.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public abstract class AbstractContextHandler {

	/**
	 * 创建抽象上下文处理器.
	 */
	protected AbstractContextHandler() {
	}

	/**
	 * 设置内容.
	 *
	 * @param key   key
	 * @param value 内容
	 */
	@SuppressWarnings("SameParameterValue")
	public static void set(String key, Object value) {
		ContextAttributesHolder.get().put(key, value);
	}

	/**
	 * 获取内容.
	 *
	 * @param key key
	 * @param <T> 值类型
	 * @return 内容
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T get(String key) {
		return (T) ContextAttributesHolder.get().get(key);
	}

	/**
	 * 从当前线程获取内容拷贝.
	 *
	 * @return 内容拷贝结果
	 */
	public static ContextAttributes getCopyOfContext() {
		return ContextAttributesHolder.get().copy();
	}

	/**
	 * 设置上下文属性.
	 *
	 * @param contextAttributes 上下文属性
	 */
	public static void setContextAttributes(ContextAttributes contextAttributes) {
		ContextAttributesHolder.set(contextAttributes);
	}

	/**
	 * 清除当前内容.
	 */
	public static void clean() {
		ContextAttributesHolder.get().clear();
	}

	/**
	 * 获取token.
	 *
	 * @return token
	 */
	@Nullable
	public static String getToken() {
		return get(Constants.TOKEN);
	}

	/**
	 * 设置token.
	 *
	 * @param token token
	 */
	public static void setToken(String token) {
		set(Constants.TOKEN, token);
	}

	/**
	 * 获取当前操作者.
	 *
	 * @return 当前操作者
	 */
	@Nullable
	public static String getCurrentOperator() {
		return get(Constants.CURRENT_OPERATOR);
	}

	/**
	 * 设置当前操作者.
	 *
	 * @param currentOperator 当前操作者
	 */
	public static void setCurrentOperator(String currentOperator) {
		set(Constants.CURRENT_OPERATOR, currentOperator);
	}

	/**
	 * 设置可传递内容.
	 *
	 * @param key   key
	 * @param value 内容
	 */
	@SuppressWarnings("SameParameterValue")
	public static void setTransfer(String key, String value) {
		set(Constants.ALLOW_TRANSFER_HEADER_PREFIX + key, value);
	}

	/**
	 * 获取可传递内容.
	 *
	 * @param key key
	 * @return 内容
	 */
	@Nullable
	public static String getTransfer(String key) {
		return get(Constants.ALLOW_TRANSFER_HEADER_PREFIX + key);
	}

	/**
	 * 获取所有可传递内容.
	 *
	 * @return 所有可传递内容
	 */
	public static Map<String, String> getAllTransfer() {
		Map<String, String> result = new HashMap<>();

		for (Map.Entry<Object, Object> entry : ContextAttributesHolder.get().entrySet()) {
			if (!(entry.getKey() instanceof String key) || !(entry.getValue() instanceof String value) || !key.startsWith(Constants.ALLOW_TRANSFER_HEADER_PREFIX)) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}
}
