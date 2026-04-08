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

package net.guerlab.cloud.context.core;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author guer
 */
class StringCaseTest {

	@Test
	void caseTest() {
		ContextAttributes attributes = new ContextAttributes("test");
		attributes.put("UPPERCASE", "value");
		attributes.put("lowercase", "value");

		Map<String, String> target = new HashMap<>();
		target.put("uppercase", "value");
		target.put("lowercase", "value");

		Assertions.assertIterableEquals(target.entrySet(), attributes.entrySet());
	}
}
