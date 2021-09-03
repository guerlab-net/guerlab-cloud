package net.guerlab.cloud.auth.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.properties.TestRedisTokenFactoryProperties;

/**
 * redis token 工厂
 *
 * @author guer
 */
public class TestRedisTokenFactory extends AbstractRedisTokenFactory<ITestTokenInfo, TestRedisTokenFactoryProperties> {

    /**
     * 签名前缀
     */
    public static final String PREFIX = "TEST_REDIS";

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @Override
    public Class<ITestTokenInfo> getAcceptClass() {
        return ITestTokenInfo.class;
    }

    @Override
    protected TypeReference<? extends ITestTokenInfo> getTypeReference() {
        return new TypeReference<TestTokenInfo>() {};
    }
}
