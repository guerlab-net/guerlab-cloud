/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * RedisTemplate自动配置.
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
public class RedisTemplateAutoConfigure {

	/**
	 * create RedisTemplate.
	 *
	 * @param factory      RedisConnectionFactory
	 * @param objectMapper objectMapper
	 * @return RedisTemplate
	 */
	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
		ObjectMapper mapper = objectMapper.copy();
		mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

		RedisTemplate<?, ?> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new GenericJackson2JsonRedisSerializer(mapper));
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));

		return template;
	}
}
