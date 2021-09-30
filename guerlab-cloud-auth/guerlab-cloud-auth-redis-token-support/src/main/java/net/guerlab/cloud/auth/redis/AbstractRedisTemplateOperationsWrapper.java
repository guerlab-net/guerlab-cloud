package net.guerlab.cloud.auth.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redisTemplate操作包装类
 *
 * @param <T>
 *         数据实体类型
 * @author guer
 */
public abstract class AbstractRedisTemplateOperationsWrapper<T> extends AbstractRedisOperationsWrapper<T> {

    private final RedisTemplate<String, String> redisTemplate;

    public AbstractRedisTemplateOperationsWrapper(ObjectMapper objectMapper,
            RedisTemplate<String, String> redisTemplate) {
        super(objectMapper);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected boolean put0(String key, String dataString, long timeout) {
        return Objects
                .equals(redisTemplate.opsForValue().setIfAbsent(key, dataString, timeout, TimeUnit.MILLISECONDS), true);
    }

    @Override
    protected String get0(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
