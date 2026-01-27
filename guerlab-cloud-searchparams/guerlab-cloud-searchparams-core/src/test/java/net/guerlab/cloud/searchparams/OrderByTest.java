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

package net.guerlab.cloud.searchparams;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author guer
 */
public class OrderByTest {

	static Stream<Arguments> arguments() {
		return Stream.of(
				Arguments.of("ID", true),
				Arguments.of("id", true),
				Arguments.of("user_name", true),
				Arguments.of(" user_name ", true),
				Arguments.of(" 'name' ", true),
				Arguments.of(" 'name ", false),
				Arguments.of(" name‘ ", false),
				Arguments.of(" ab'c ", false),
				Arguments.of(" name -- ", false),
				Arguments.of(" id and ", false),
				Arguments.of(" id desc ", false),
				Arguments.of(" a.b ", false),
				Arguments.of(" date() ", false)
		);
	}

	@ParameterizedTest
	@MethodSource("arguments")
	void test(String columnName, boolean accept) {
		OrderBy orderBy = OrderBy.builder().columnName(columnName).build();
		Assertions.assertEquals(accept, orderBy.accept());
	}
}
