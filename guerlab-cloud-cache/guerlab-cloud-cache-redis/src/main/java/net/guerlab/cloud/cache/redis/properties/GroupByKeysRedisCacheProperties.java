package net.guerlab.cloud.cache.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

/**
 * 按key分组redis缓存配置
 *
 * @author guer
 */
@ConfigurationProperties(prefix = GroupByKeysRedisCacheProperties.PROPERTIES_PREFIX)
public class GroupByKeysRedisCacheProperties extends HashMap<String, RedisCacheConfig> {

    /**
     * 配置前缀
     */
    public static final String PROPERTIES_PREFIX = "spring.cache.redis.configs";
}
