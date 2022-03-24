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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.core.RedisTemplate;

import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;

/**
 * 测试用redisTemplate操作包装类.
 *
 * @author guer
 */
public class TestRedisTemplateOperationsWrapper extends AbstractRedisTemplateOperationsWrapper<ITestTokenInfo> {

	public TestRedisTemplateOperationsWrapper(ObjectMapper objectMapper, RedisTemplate<String, String> redisTemplate) {
		super(objectMapper, redisTemplate);
	}

	@Override
	protected TypeReference<TestTokenInfo> getTypeReference() {
		return new TypeReference<>() { };
	}
}
