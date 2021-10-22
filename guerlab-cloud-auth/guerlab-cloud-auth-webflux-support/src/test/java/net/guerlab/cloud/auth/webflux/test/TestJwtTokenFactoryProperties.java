package net.guerlab.cloud.auth.webflux.test;

import net.guerlab.cloud.auth.properties.JwtTokenFactoryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guer
 */
@ConfigurationProperties(prefix = TestJwtTokenFactoryProperties.PREFIX)
public class TestJwtTokenFactoryProperties extends JwtTokenFactoryProperties {

    public static final String PREFIX = "auth.test.token-factory.jwt";
}
