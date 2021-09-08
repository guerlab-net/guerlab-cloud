package net.guerlab.cloud.auth.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 测试用redisTemplate操作包装类
 *
 * @author guer
 */
public class TestRedisTemplateOperationsWrapper extends AbstractRedisTemplateOperationsWrapper<ITestTokenInfo> {

    public TestRedisTemplateOperationsWrapper(ObjectMapper objectMapper, RedisTemplate<String, String> redisTemplate) {
        super(objectMapper, redisTemplate);
    }

    @Override
    protected TypeReference<? extends ITestTokenInfo> getTypeReference() {
        return new TypeReference<TestTokenInfo>() {};
    }
}
