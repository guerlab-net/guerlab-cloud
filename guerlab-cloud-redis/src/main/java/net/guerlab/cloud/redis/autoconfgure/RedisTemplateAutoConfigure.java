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

package net.guerlab.cloud.redis.autoconfgure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.redisson.spring.starter.RedissonAutoConfiguration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * RedisTemplate自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
@AutoConfigureBefore({
		RedisAutoConfiguration.class,
		RedissonAutoConfiguration.class
})
public class RedisTemplateAutoConfigure {

	/**
	 * view to {@link org.redisson.spring.starter.RedissonAutoConfiguration#redissonConnectionFactory}.
	 */
	@Bean
	@ConditionalOnMissingBean(RedisConnectionFactory.class)
	public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
		log.debug("init redissonConnectionFactory");
		return new RedissonConnectionFactory(redisson);
	}

	/**
	 * create RedisTemplate.
	 *
	 * @param factory      RedisConnectionFactory
	 * @param objectMapper objectMapper
	 * @return RedisTemplate
	 */
	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
		log.debug("init redisTemplate");
		ObjectMapper mapper = objectMapper.copy();
		mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

		GenericJackson2JsonRedisSerializer objectSerializer = new GenericJackson2JsonRedisSerializer(mapper);
		KeyRedisSerializerProxy keyRedisSerializerProxy = new KeyRedisSerializerProxy(objectSerializer);

		RedisTemplate<?, ?> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(keyRedisSerializerProxy);
		template.setValueSerializer(objectSerializer);

		return template;
	}

	/**
	 * create StringRedisTemplate.
	 *
	 * @param factory RedisConnectionFactory
	 * @return StringRedisTemplate
	 */
	@Bean
	@ConditionalOnMissingBean(StringRedisTemplate.class)
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		log.debug("init stringRedisTemplate");
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(factory);
		return template;
	}

}
