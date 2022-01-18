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
package net.guerlab.cloud.auth.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.factory.TestRedisTokenFactory;
import net.guerlab.cloud.auth.properties.TestRedisTokenFactoryProperties;
import net.guerlab.cloud.auth.redis.RedisOperationsWrapper;
import net.guerlab.cloud.auth.redis.TestRedisTemplateOperationsWrapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 测试用redis token自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ TestRedisTokenFactoryProperties.class })
public class TestRedisTokenAutoconfigure {

    /**
     * 构造测试用redisTemplate操作包装类
     *
     * @param objectMapper
     *         objectMapper
     * @param redisTemplate
     *         redisTemplate
     * @return 测试用redisTemplate操作包装类
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public TestRedisTemplateOperationsWrapper testRedisTemplateOperationsWrapper(ObjectMapper objectMapper,
            RedisTemplate<String, String> redisTemplate) {
        return new TestRedisTemplateOperationsWrapper(objectMapper, redisTemplate);
    }

    /**
     * 构造测试用redis token 工厂
     *
     * @param redisOperationsWrapper
     *         redis操作包装对象
     * @return 测试用redis token 工厂
     */
    @Bean
    public TestRedisTokenFactory testRedisTokenFactory(RedisOperationsWrapper<ITestTokenInfo> redisOperationsWrapper) {
        return new TestRedisTokenFactory(redisOperationsWrapper);
    }
}
