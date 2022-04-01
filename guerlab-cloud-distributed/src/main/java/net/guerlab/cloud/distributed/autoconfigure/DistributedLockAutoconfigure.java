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

package net.guerlab.cloud.distributed.autoconfigure;

import org.redisson.api.RedissonClient;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.distributed.aspect.DistributedLockAspect;

/**
 * 幂等自动配置.
 *
 * @author guer
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class DistributedLockAutoconfigure {

	/**
	 * 构造分布式锁处理切面.
	 *
	 * @param redissonClient redissonClient
	 * @param messageSource  messageSource
	 * @return 分布式锁处理切面
	 */
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Bean
	public DistributedLockAspect distributedLockAspect(RedissonClient redissonClient, MessageSource messageSource) {
		return new DistributedLockAspect(redissonClient, messageSource);
	}
}
