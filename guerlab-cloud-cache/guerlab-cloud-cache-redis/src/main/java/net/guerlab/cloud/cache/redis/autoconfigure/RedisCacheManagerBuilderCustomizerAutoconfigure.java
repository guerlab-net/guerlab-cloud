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
package net.guerlab.cloud.cache.redis.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.cloud.cache.redis.properties.GroupByKeysRedisCacheProperties;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * redis缓存管理定制自动配置
 *
 * @author guer
 */
@EnableCaching
@Configurable
@EnableConfigurationProperties(GroupByKeysRedisCacheProperties.class)
@AutoConfigureBefore(RedisCacheConfiguration.class)
public class RedisCacheManagerBuilderCustomizerAutoconfigure {

    private final GroupByKeysRedisCacheProperties properties;

    private final SerializationPair<String> keySerializationPair = SerializationPair.fromSerializer(
            new StringRedisSerializer());

    private final SerializationPair<Object> valueSerializationPair;

    /**
     * 初始化redis缓存管理定制自动配置
     *
     * @param objectMapper
     *         objectMapper
     * @param properties
     *         按key分组redis缓存配置
     */
    public RedisCacheManagerBuilderCustomizerAutoconfigure(ObjectMapper objectMapper,
            GroupByKeysRedisCacheProperties properties) {
        this.properties = properties;

        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisSerializer.setObjectMapper(objectMapper);
        valueSerializationPair = SerializationPair.fromSerializer(redisSerializer);
    }

    /**
     * 构造默认redis缓存配置
     *
     * @param cacheProperties
     *         缓存配置
     * @return 默认redis缓存配置
     */
    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration(CacheProperties cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeKeysWith(keySerializationPair);
        config = config.serializeValuesWith(valueSerializationPair);

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }

    /**
     * redis缓存管理构造器定制
     *
     * @return redis缓存管理构造器定制
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = properties.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().build(keySerializationPair, valueSerializationPair)));
        return builder -> builder.withInitialCacheConfigurations(cacheConfigurations);
    }
}
