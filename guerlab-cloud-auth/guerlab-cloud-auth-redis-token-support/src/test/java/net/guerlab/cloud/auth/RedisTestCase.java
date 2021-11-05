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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.autoconfig.TestRedisTokenAutoconfigure;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.factory.AbstractTokenFactory;
import net.guerlab.cloud.auth.factory.TestRedisTokenFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

/**
 * 测试类
 *
 * @author guer
 */
@Slf4j
class RedisTestCase {

    private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void setUp() {
        context = new AnnotationConfigApplicationContext();
        TestPropertyValues.of("auth.test.token-factory.redis.access-token-key-length=6").applyTo(context);
        TestPropertyValues.of("auth.test.token-factory.redis.refresh-token-key-length=6").applyTo(context);
        context.registerBean("objectMapper", ObjectMapper.class);
        context.register(RedisAutoConfiguration.class, TestRedisTokenAutoconfigure.class);
        context.refresh();
    }

    @AfterAll
    static void tearDown() {
        Optional.ofNullable(context).ifPresent(AnnotationConfigApplicationContext::close);
    }

    @Test
    void redis() {
        accessToken(context.getBean(TestRedisTokenFactory.class));
        refreshToken(context.getBean(TestRedisTokenFactory.class));
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
