/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.auth;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.autoconfig.TestAuthAutoconfigure;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.factory.AbstractTokenFactory;
import net.guerlab.cloud.auth.factory.TestJwtTokenFactory;
import net.guerlab.cloud.auth.factory.TestMd5TokenFactory;
import net.guerlab.cloud.auth.factory.TestRc4TokenFactory;
import net.guerlab.cloud.rsa.RsaKeys;
import net.guerlab.cloud.rsa.RsaUtils;
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
class JwtTestCase {

    private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void setUp() {
        RsaKeys rsaKeys = RsaUtils.buildKeys();

        context = new AnnotationConfigApplicationContext();
        context.register(TestAuthAutoconfigure.class, TestJwtTokenFactory.class, TestMd5TokenFactory.class,
                TestRc4TokenFactory.class);
        TestPropertyValues.of(
                        "auth.test.token-factory.jwt.access-token-key.public-key=" + rsaKeys.getPublicKeyFormattedContent())
                .applyTo(context);
        TestPropertyValues.of("auth.test.token-factory.jwt.access-token-key.private-key=" + rsaKeys.getPrivateKey())
                .applyTo(context);
        TestPropertyValues.of(
                        "auth.test.token-factory.jwt.refresh-token-key.public-key=" + rsaKeys.getPublicKeyFormattedContent())
                .applyTo(context);
        TestPropertyValues.of("auth.test.token-factory.jwt.refresh-token-key.private-key=" + rsaKeys.getPrivateKey())
                .applyTo(context);
        context.refresh();
    }

    @AfterAll
    static void tearDown() {
        Optional.ofNullable(context).ifPresent(AnnotationConfigApplicationContext::close);
    }

    @Test
    void jwt() {
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
