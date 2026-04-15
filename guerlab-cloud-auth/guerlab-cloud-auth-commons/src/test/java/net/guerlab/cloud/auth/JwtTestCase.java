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
import net.guerlab.cloud.auth.factory.TestJwtTokenFactory;
import net.guerlab.cloud.rsa.RsaKeys;
import net.guerlab.cloud.rsa.RsaUtils;

/**
 * 测试类.
 *
 * @author guer
 */
@Slf4j
@SpringBootTest(
		classes = {
				TestAuthAutoConfigure.class,
				TestJwtTokenFactory.class,
				RefreshAutoConfiguration.class
		}
)
class JwtTestCase {

	private static final TestTokenInfo INFO = new TestTokenInfo(1L, "tester");

	@Resource
	private TestJwtTokenFactory tokenFactory;

	@DynamicPropertySource
	static void rsaProperties(DynamicPropertyRegistry registry) {
		RsaKeys rsaKeys = RsaUtils.buildKeys();
		registry.add("auth.test.token-factory.jwt.access-token-key.public-key", rsaKeys::getPublicKeyFormattedContent);
		registry.add("auth.test.token-factory.jwt.access-token-key.private-key", rsaKeys::getPrivateKey);

		registry.add("auth.test.token-factory.jwt.refresh-token-key.public-key", rsaKeys::getPublicKeyFormattedContent);
		registry.add("auth.test.token-factory.jwt.refresh-token-key.private-key", rsaKeys::getPrivateKey);
	}

	@Test
	void jwt() {
		accessToken();
		refreshToken();
	}

	private void accessToken() {
		TokenInfo accessToken = tokenFactory.generateByAccessToken(INFO);
		log.debug("accessToken: {}", accessToken);
		ITestTokenInfo parseInfo = tokenFactory.parseByAccessToken(accessToken.getToken());
		check(parseInfo);
	}

	private void refreshToken() {
		TokenInfo refreshToken = tokenFactory.generateByRefreshToken(INFO);
		log.debug("refreshToken: {}", refreshToken);
		ITestTokenInfo parseInfo = tokenFactory.parseByRefreshToken(refreshToken.getToken());
		check(parseInfo);
	}

	private void check(ITestTokenInfo parseInfo) {
		log.debug("parseInfo: {}", parseInfo);
		Assertions.assertEquals(parseInfo.getUserId(), INFO.getUserId());
	}
}
