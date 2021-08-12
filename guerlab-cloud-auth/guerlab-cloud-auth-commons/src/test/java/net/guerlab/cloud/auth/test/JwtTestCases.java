package net.guerlab.cloud.auth.test;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.factory.AbstractTokenFactory;
import net.guerlab.cloud.auth.test.autoconfig.TestAuthAutoconfigure;
import net.guerlab.cloud.auth.test.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.test.domain.TestTokenInfo;
import net.guerlab.cloud.auth.test.factory.TestJwtTokenFactory;
import net.guerlab.cloud.auth.test.factory.TestMd5TokenFactory;
import net.guerlab.cloud.auth.test.factory.TestRc4TokenFactory;
import net.guerlab.cloud.commons.entity.RsaKeys;
import net.guerlab.cloud.commons.util.RsaUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

/**
 * 测试类
 *
 * @author guer
 */
@Slf4j
public class JwtTestCases {

    private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void setUp() {
        RsaKeys rsaKeys = RsaUtils.buildKeys();

        context = new AnnotationConfigApplicationContext();
        context.register(TestAuthAutoconfigure.class, TestJwtTokenFactory.class, TestMd5TokenFactory.class,
                TestRc4TokenFactory.class);
        TestPropertyValues
                .of("auth.test.token-factory.jwt.access-token-key.public-key=" + rsaKeys.getPublicKeyFormattedContent())
                .applyTo(context);
        TestPropertyValues.of("auth.test.token-factory.jwt.access-token-key.private-key=" + rsaKeys.getPrivateKey())
                .applyTo(context);
        TestPropertyValues.of("auth.test.token-factory.jwt.refresh-token-key.public-key=" + rsaKeys
                .getPublicKeyFormattedContent()).applyTo(context);
        TestPropertyValues.of("auth.test.token-factory.jwt.refresh-token-key.private-key=" + rsaKeys.getPrivateKey())
                .applyTo(context);
        context.refresh();
    }

    @AfterAll
    static void tearDown() {
        Optional.ofNullable(context).ifPresent(AnnotationConfigApplicationContext::close);
    }

    @Test
    public void jwt() {
        accessToken(context.getBean(TestJwtTokenFactory.class));
        refreshToken(context.getBean(TestJwtTokenFactory.class));
    }

    private void accessToken(AbstractTokenFactory<ITestTokenInfo, ?> factory) {
        TokenInfo accessToken = factory.generateByAccessToken(INFO);
        log.debug("accessToken: {}", accessToken);
        ITestTokenInfo parseInfo = factory.parseByAccessToken(accessToken.getToken());
        log.debug("parseInfo: {}", parseInfo);
        Assertions.assertEquals(parseInfo.getUserId(), INFO.getUserId());
    }

    private void refreshToken(AbstractTokenFactory<ITestTokenInfo, ?> factory) {
        TokenInfo refreshToken = factory.generateByRefreshToken(INFO);
        log.debug("refreshToken: {}", refreshToken);
        ITestTokenInfo parseInfo = factory.parseByRefreshToken(refreshToken.getToken());
        log.debug("parseInfo: {}", parseInfo);
        Assertions.assertEquals(parseInfo.getUserId(), INFO.getUserId());
    }
}
