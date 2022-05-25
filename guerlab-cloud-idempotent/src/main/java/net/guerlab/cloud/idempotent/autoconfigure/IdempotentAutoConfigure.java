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

package net.guerlab.cloud.idempotent.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import net.guerlab.cloud.idempotent.aspect.IdempotentAspect;

/**
 * 幂等自动配置.
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class IdempotentAutoConfigure {

	/**
	 * 构造幂等处理切面.
	 *
	 * @param redisTemplate StringRedisTemplate
	 * @param messageSource messageSource
	 * @return 幂等处理切面
	 */
	@Bean
	public IdempotentAspect idempotentAop(StringRedisTemplate redisTemplate, MessageSource messageSource) {
		return new IdempotentAspect(redisTemplate, messageSource);
	}
}
