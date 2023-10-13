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

package net.guerlab.cloud.cache.redis.properties;

import java.time.Duration;

import lombok.Data;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * redis缓存配置.
 *
 * @author guer
 */
@Data
public class RedisCacheConfig {

	/**
	 * 有效期.
	 */
	private Long ttl;

	/**
	 * 缓存null值.
	 */
	private boolean cacheNullValues = true;

	/**
	 * key前缀.
	 */
	private String keyPrefix;

	/**
	 * 是否使用前缀.
	 */
	private boolean usePrefix = true;

	/**
	 * 构造RedisCacheConfiguration.
	 *
	 * @param keySerializationPair   keySerializationPair
	 * @param valueSerializationPair valueSerializationPair
	 * @return RedisCacheConfiguration
	 */
	public RedisCacheConfiguration build(SerializationPair<String> keySerializationPair,
			SerializationPair<Object> valueSerializationPair) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		config = config.serializeKeysWith(keySerializationPair);
		config = config.serializeValuesWith(valueSerializationPair);

		if (ttl != null && ttl > 0) {
			config = config.entryTtl(Duration.ofSeconds(ttl));
		}
		if (keyPrefix != null) {
			config = config.prefixCacheNameWith(keyPrefix);
		}
		if (!cacheNullValues) {
			config = config.disableCachingNullValues();
		}
		if (!usePrefix) {
			config = config.disableKeyPrefix();
		}

		return config;
	}
}
