/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.searchparams;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import net.guerlab.cloud.core.util.SpringUtils;

/**
 * 自定义sql提供器加载测试.
 *
 * @author guer
 */
@Slf4j
@SpringBootTest(
		classes = {
				TestAutoConfigure.class,
				SpringUtils.class
		},
		properties = {
				"logging.level.net.guerlab.cloud.searchparams=DEBUG"
		}
)
public class SqlProviderLoadTest {

	@BeforeEach
	public void before() {
		SearchParamsUtils.cleanSqlProviderCache(TestSqlProvider.class);
	}

	@Test
	public void byClass() {
		List<SqlProvider> providers = SearchParamsUtils.loadSqlProviders(ClassTestSqlProvider.class);
		Assertions.assertTrue(providers.stream()
				.anyMatch(provider -> provider.getClass().isAssignableFrom(ClassTestSqlProvider.class)));
	}

	@Test
	public void byServiceLoad() {
		List<SqlProvider> providers = SearchParamsUtils.loadSqlProviders(TestSqlProvider.class);
		Assertions.assertTrue(providers.stream()
				.anyMatch(provider -> provider.getClass().isAssignableFrom(ServiceLoadTestSqlProvider.class)));
	}

	@Test
	public void bySpring() {
		List<SqlProvider> providers = SearchParamsUtils.loadSqlProviders(TestSqlProvider.class);
		Assertions.assertTrue(providers.stream()
				.anyMatch(provider -> provider.getClass().isAssignableFrom(BeanTestSqlProvider.class)));
	}
}
