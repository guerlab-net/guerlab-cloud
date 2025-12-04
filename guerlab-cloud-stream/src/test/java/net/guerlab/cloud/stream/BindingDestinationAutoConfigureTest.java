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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.cloud.stream.config.BindingServiceProperties;

/**
 * BindingDestinationAutoConfigure配置测试.
 *
 * @author guer
 */
class BindingDestinationAutoConfigureTest {

	private Map<String, String> bindingDestinations;

	private BindingServiceProperties bindingServiceProperties;

	@BeforeEach
	public void init() {
		bindingDestinations = new HashMap<>(3);
		bindingDestinations.put("testAdd", "test.add");
		bindingDestinations.put("testDelete", "test.delete");
		bindingDestinations.put("testUpdate", "test.update");
		bindingServiceProperties = new BindingServiceProperties();
		bindingServiceProperties.setBindings(new HashMap<>());
	}

	@Test
	void input() {
		InputBindingDestinationAutoConfigure configure = new InputBindingDestinationAutoConfigure(bindingDestinations);
		configure.initBindingDestination(bindingServiceProperties);

		Assertions.assertArrayEquals(new String[] {"onTestAdd-in-0", "onTestDelete-in-0", "onTestUpdate-in-0"},
				bindingServiceProperties.getBindings().keySet().toArray(new String[] {}));
	}

	@Test
	void output() {
		OutputBindingDestinationAutoConfigure configure = new OutputBindingDestinationAutoConfigure(
				bindingDestinations);
		configure.initBindingDestination(bindingServiceProperties);

		Assertions.assertArrayEquals(new String[] {"testAdd-out-0", "testDelete-out-0", "testUpdate-out-0"},
				bindingServiceProperties.getBindings().keySet().toArray(new String[] {}));
	}
}
