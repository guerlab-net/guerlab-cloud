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

package net.guerlab.cloud.cache.redis.properties;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 按key分组redis缓存配置.
 *
 * @author guer
 */
@ConfigurationProperties(prefix = GroupByKeysRedisCacheProperties.PROPERTIES_PREFIX)
public class GroupByKeysRedisCacheProperties extends HashMap<String, RedisCacheConfig> {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = "spring.cache.redis.configs";
}
