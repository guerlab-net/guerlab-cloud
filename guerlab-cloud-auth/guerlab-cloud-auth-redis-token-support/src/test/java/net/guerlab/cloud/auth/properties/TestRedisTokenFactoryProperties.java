package net.guerlab.cloud.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 测试用redis token 配置
 *
 * @author guer
 */
@RefreshScope
@ConfigurationProperties(prefix = TestRedisTokenFactoryProperties.PREFIX)
public class TestRedisTokenFactoryProperties extends RedisTokenFactoryProperties {

    public final static String PREFIX = "auth.test.token-factory.redis";
}
