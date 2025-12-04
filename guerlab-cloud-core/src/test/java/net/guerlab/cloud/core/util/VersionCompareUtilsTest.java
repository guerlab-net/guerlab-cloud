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

package net.guerlab.cloud.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 版本控制测试.
 *
 * @author guer
 */
class VersionCompareUtilsTest {

	private static final String[] ORIGINS = new String[] {
			"1.0", "1.0.1", "1.1", "1.1.1", "1.2", "1.2.1", "str"
	};
	private static final String[] RANGES = new String[] {
			"1.0.0", "1.0.1", "[1.0.0, 1.2.1]", "(1.0.0, 1.2.1)", "[1.0.0, 1.2.1)", "(1.0.0, 1.2.1]", "string", "[str, str]"
	};
	private static final Boolean[][] EXPECT = new Boolean[][] {
			new Boolean[] {true, false, true, false, true, false, false, false},
			new Boolean[] {false, true, true, true, true, true, false, false},
			new Boolean[] {false, false, true, true, true, true, false, false},
			new Boolean[] {false, false, true, true, true, true, false, false},
			new Boolean[] {false, false, true, true, true, true, false, false},
			new Boolean[] {false, false, true, false, false, true, false, false},
			new Boolean[] {false, false, false, false, false, false, false, false},
	};

	static Stream<Arguments> testSourceProvider() {
		List<Arguments> arguments = new ArrayList<>();

		for (int o = 0; o < ORIGINS.length; o++) {
			for (int r = 0; r < RANGES.length; r++) {
				arguments.add(Arguments.arguments(ORIGINS[o], RANGES[r], EXPECT[o][r]));
			}
		}

		return arguments.stream();
	}

	/**
	 * 匹配测试.
	 */
	@ParameterizedTest
	@MethodSource("testSourceProvider")
	void match(String o, String r, boolean result) {
		Assertions.assertEquals(result, VersionCompareUtils.match(o, r));
	}
}
