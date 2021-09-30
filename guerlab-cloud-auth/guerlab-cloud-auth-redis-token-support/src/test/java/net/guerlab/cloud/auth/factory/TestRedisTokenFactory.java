package net.guerlab.cloud.auth.factory;

import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.properties.TestRedisTokenFactoryProperties;
import net.guerlab.cloud.auth.redis.RedisOperationsWrapper;

/**
 * 测试用redis token 工厂
 *
 * @author guer
 */
public class TestRedisTokenFactory extends AbstractRedisTokenFactory<ITestTokenInfo, TestRedisTokenFactoryProperties> {

    /**
     * 签名前缀
     */
    public static final String PREFIX = "TEST_REDIS";

    public TestRedisTokenFactory(RedisOperationsWrapper<ITestTokenInfo> redisOperationsWrapper) {
        super(redisOperationsWrapper);
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @Override
    public Class<ITestTokenInfo> getAcceptClass() {
        return ITestTokenInfo.class;
    }
}
