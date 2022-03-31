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

package org.springframework.boot.autoconfigure.data.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.properties.RedissonConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson链接工厂自动配置.
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedissonConfig.class)
@AutoConfigureBefore(RedisTemplateAutoconfigure.class)
public class RedissonConnectionFactoryAutoconfigure {

	/**
	 * 创建RedissonClient.
	 *
	 * @param config
	 *         Redisson配置
	 * @return RedissonClient实例
	 */
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson(RedissonConfig config) {
		return Redisson.create(config);
	}

	/**
	 * 创建RedissonConnectionFactory实例.
	 *
	 * @param redisson
	 *         RedissonClient实例
	 * @return RedissonConnectionFactory实例
	 */
	@Bean
	public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
		return new RedissonConnectionFactory(redisson);
	}
}
