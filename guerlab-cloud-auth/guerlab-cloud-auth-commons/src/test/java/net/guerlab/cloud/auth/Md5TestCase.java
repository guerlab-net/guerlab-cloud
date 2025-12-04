/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.guerlab.cloud.auth.autoconfigure.TestAuthAutoConfigure;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.factory.AbstractTokenFactory;
import net.guerlab.cloud.auth.factory.TestJwtTokenFactory;
import net.guerlab.cloud.auth.factory.TestMd5TokenFactory;
import net.guerlab.cloud.auth.factory.TestRc4TokenFactory;

/**
 * 测试类.
 *
 * @author guer
 */
@Slf4j
class Md5TestCase {

	private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

	private static AnnotationConfigApplicationContext context;

	@BeforeAll
	static void setUp() {
		context = new AnnotationConfigApplicationContext();
		context.register(TestAuthAutoConfigure.class, TestJwtTokenFactory.class, TestMd5TokenFactory.class,
				TestRc4TokenFactory.class);
		TestPropertyValues.of("auth.test.token-factory.md5.access-token-key=test-access-md5").applyTo(context);
		TestPropertyValues.of("auth.test.token-factory.md5.refresh-token-key=test-refresh-md5").applyTo(context);
		context.refresh();
	}

	@AfterAll
	static void tearDown() {
		Optional.ofNullable(context).ifPresent(AnnotationConfigApplicationContext::close);
	}

	@Test
	void md5() {
		test(context.getBean(TestMd5TokenFactory.class));
	}

	private void test(AbstractTokenFactory<ITestTokenInfo, ?> factory) {
		TokenInfo accessToken = factory.generateByAccessToken(INFO);
		log.debug("accessToken: {}", accessToken);
		ITestTokenInfo parseInfo = factory.parseByAccessToken(accessToken.getToken());
		log.debug("parseInfo: {}", parseInfo);
		Assertions.assertEquals(parseInfo.getUserId(), INFO.getUserId());
	}
}
