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

package net.guerlab.cloud.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;

/**
 * BindingDestinationAutoConfigure重复key测试.
 *
 * @author guer
 */
class RepeatKeyBindingDestinationAutoConfigureTest {

	private Map<String, String> bindingDestinations;

	private BindingServiceProperties bindingServiceProperties;

	@BeforeEach
	public void init() {
		bindingDestinations = new HashMap<>(3);
		bindingDestinations.put("testAdd", "test.add");

		Map<String, BindingProperties> binding = new HashMap<>();
		binding.put("testAdd-out-0", new BindingProperties());

		bindingServiceProperties = new BindingServiceProperties();
		bindingServiceProperties.setBindings(binding);
	}

	@Test
	void test() {
		OutputBindingDestinationAutoConfigure configure = new OutputBindingDestinationAutoConfigure(
				bindingDestinations);
		configure.initBindingDestination(bindingServiceProperties);

		Set<String> keys = bindingServiceProperties.getBindings().keySet();

		Assertions.assertEquals(1, keys.size());
		Assertions.assertArrayEquals(new String[] {"testAdd-out-0"}, keys.toArray(new String[] {}));
	}
}
