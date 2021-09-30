package net.guerlab.cloud.cache.redis.properties;

import lombok.Data;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

/**
 * redis缓存配置
 *
 * @author guer
 */
@Data
public class RedisCacheConfig {

    /**
     * 有效期
     */
    private Long ttl;

    /**
     * 缓存null值
     */
    private boolean cacheNullValues = true;

    /**
     * key前缀
     */
    private String keyPrefix;

    /**
     * 是否使用前缀
     */
    private boolean usePrefix = true;

    /**
     * 构造RedisCacheConfiguration
     *
     * @param keySerializationPair
     *         keySerializationPair
     * @param valueSerializationPair
     *         valueSerializationPair
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
