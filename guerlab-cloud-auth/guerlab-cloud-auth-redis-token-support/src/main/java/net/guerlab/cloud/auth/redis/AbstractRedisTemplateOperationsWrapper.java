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

package net.guerlab.cloud.auth.redis;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

/**
 * redisTemplate操作包装类.
 *
 * @param <T> 数据实体类型
 * @author guer
 */
public abstract class AbstractRedisTemplateOperationsWrapper<T> extends AbstractRedisOperationsWrapper<T> {

	/**
	 * redisTemplate.
	 */
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * 初始化redisTemplate操作包装类.
	 *
	 * @param objectMapper  objectMapper
	 * @param redisTemplate redisTemplate
	 */
	protected AbstractRedisTemplateOperationsWrapper(ObjectMapper objectMapper,
			RedisTemplate<String, String> redisTemplate) {
		super(objectMapper);
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected boolean put0(String key, String dataString, long timeout) {
		return Objects.equals(redisTemplate.opsForValue().setIfAbsent(key, dataString, timeout, TimeUnit.MILLISECONDS),
				true);
	}

	@Nullable
	@Override
	protected String get0(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
