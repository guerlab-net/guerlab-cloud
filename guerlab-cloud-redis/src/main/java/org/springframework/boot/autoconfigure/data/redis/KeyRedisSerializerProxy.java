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

package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

/**
 * key序列化代理.
 *
 * @author guer
 */
public class KeyRedisSerializerProxy implements RedisSerializer<Object> {

	/**
	 * 对象序列化处理.
	 */
	private final RedisSerializer<Object> objectSerializer;

	/**
	 * 字符串序列化处理.
	 */
	private final StringRedisSerializer stringSerializer;

	/**
	 * key序列化代理.
	 *
	 * @param objectSerializer 对象序列化处理
	 */
	public KeyRedisSerializerProxy(RedisSerializer<Object> objectSerializer) {
		this.objectSerializer = objectSerializer;
		this.stringSerializer = new StringRedisSerializer();
	}

	@Nullable
	@Override
	public byte[] serialize(@Nullable Object value) throws SerializationException {
		if (value instanceof String string) {
			return stringSerializer.serialize(string);
		}
		return objectSerializer.serialize(value);
	}

	@Nullable
	@Override
	public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
		return objectSerializer.deserialize(bytes);
	}
}
