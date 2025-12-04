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

package net.guerlab.cloud.auth.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象redis操作包装对象.
 *
 * @param <T> 数据实体类型
 * @author guer
 */
public abstract class AbstractRedisOperationsWrapper<T> implements RedisOperationsWrapper<T> {

	/**
	 * objectMapper.
	 */
	private final ObjectMapper objectMapper;

	/**
	 * 初始化redis操作包装对象.
	 *
	 * @param objectMapper objectMapper
	 */
	protected AbstractRedisOperationsWrapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean put(String key, T entity, long timeout) {
		String dataString;
		try {
			dataString = objectMapper.writeValueAsString(entity);
		}
		catch (Exception e) {
			throw new ApplicationException(e.getLocalizedMessage(), e);
		}
		return put0(key, dataString, timeout);
	}

	/**
	 * 保存对象字符串.
	 *
	 * @param key        key
	 * @param dataString 对象字符串
	 * @param timeout    超时时间
	 * @return 是否成功
	 */
	protected abstract boolean put0(String key, String dataString, long timeout);

	@Nullable
	@Override
	public T get(String key) {
		String data = get0(key);
		if (data == null) {
			return null;
		}
		try {
			return objectMapper.readValue(data, getTypeReference());
		}
		catch (Exception e) {
			throw new ApplicationException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 获取对象字符串.
	 *
	 * @param key key
	 * @return 对象字符串
	 */
	@Nullable
	protected abstract String get0(String key);

	/**
	 * 获取数据对象类型引用.
	 *
	 * @return 数据对象类型引用
	 */
	protected abstract TypeReference<T> getTypeReference();
}
