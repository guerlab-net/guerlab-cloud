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

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import net.guerlab.cloud.auth.autoconfigure.TestAuthAutoConfigure;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.factory.TestMd5TokenFactory;

/**
 * 测试类.
 *
 * @author guer
 */
@Slf4j
@SpringBootTest(
		classes = {
				TestAuthAutoConfigure.class,
				TestMd5TokenFactory.class,
				RefreshAutoConfiguration.class
		}
)
class Md5TestCase {

	private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

	@Resource
	private TestMd5TokenFactory tokenFactory;

	@DynamicPropertySource
	static void rsaProperties(DynamicPropertyRegistry registry) {
		registry.add("auth.test.token-factory.md5.access-token-key", () -> "test-access-md5");
		registry.add("auth.test.token-factory.md5.refresh-token-key", () -> "test-refresh-md5");
	}

	@Test
	void md5() {
		TokenInfo accessToken = tokenFactory.generateByAccessToken(INFO);
		log.debug("accessToken: {}", accessToken);
		ITestTokenInfo parseInfo = tokenFactory.parseByAccessToken(accessToken.getToken());
		log.debug("parseInfo: {}", parseInfo);
		Assertions.assertEquals(parseInfo.getUserId(), INFO.getUserId());
	}
}
