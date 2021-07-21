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
public class TestCases {

    private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(TestAuthAutoconfigure.class, TestJwtTokenFactory.class, TestMd5TokenFactory.class,
                TestRc4TokenFactory.class);
        String[] types = new String[] { "jwt", "md5", "rc4" };
        for (String type : types) {
            TestPropertyValues.of("auth.test.token-factory." + type + ".access-token-key=test-access-" + type)
                    .applyTo(context);
            TestPropertyValues.of("auth.test.token-factory." + type + ".refresh-token-key=test-refresh-" + type)
                    .applyTo(context);
        }
        context.refresh();
    }

    @AfterAll
    static void tearDown() {
        Optional.ofNullable(context).ifPresent(AnnotationConfigApplicationContext::close);
    }

    @Test
    public void jwt() {
        test(context.getBean(TestJwtTokenFactory.class));
    }

    @Test
    public void md5() {
        test(context.getBean(TestMd5TokenFactory.class));
    }

    @Test
    public void rc4() {
        test(context.getBean(TestRc4TokenFactory.class));
    }

    private void test(AbstractTokenFactory<ITestTokenInfo, ?> factory) {
        TokenInfo accessToken = factory.generateByAccessToken(INFO);
        log.debug("accessToken: {}", accessToken);
        ITestTokenInfo parseInfo = factory.parseByAccessToken(accessToken.getToken());
        log.debug("parseInfo: {}", parseInfo);
        Assertions.assertEquals(parseInfo.getUserId(), INFO.getUserId());
    }
}
