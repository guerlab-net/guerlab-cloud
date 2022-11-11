/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author guer
 */
class VersionTests {

	static Stream<Arguments> formatVersionProvider() {
		List<Arguments> arguments = new ArrayList<>();

		arguments.add(Arguments.arguments("2022.5.0", "2022.5.0"));
		arguments.add(Arguments.arguments("2022.5.0-SNAPSHOT", "2022.5.0"));
		arguments.add(Arguments.arguments("2022.5.0-1.0", "2022.5.0"));
		arguments.add(Arguments.arguments("2022.5.0-1.0-SNAPSHOT", "2022.5.0"));
		arguments.add(Arguments.arguments("SNAPSHOT", null));
		arguments.add(Arguments.arguments("SNAPSHOT-2022.5.0", "2022.5.0"));
		arguments.add(Arguments.arguments("SNAPSHOT-2022.5.0-5.1", "2022.5.0"));

		return arguments.stream();
	}

	static Stream<Arguments> parseProvider() {
		List<Arguments> arguments = new ArrayList<>();

		arguments.add(Arguments.arguments("2022.5.0", "2022.5.0"));
		arguments.add(Arguments.arguments("2022.5.0-SNAPSHOT", "2022.5.0"));
		arguments.add(Arguments.arguments("2022.5.0-1.0", "2022.5.0"));
		arguments.add(Arguments.arguments("2022.5.0-1.0-SNAPSHOT", "2022.5.0"));
		arguments.add(Arguments.arguments("SNAPSHOT", "null"));
		arguments.add(Arguments.arguments("SNAPSHOT-2022.5.0", "2022.5.0"));
		arguments.add(Arguments.arguments("SNAPSHOT-2022.5.0-5.1", "2022.5.0"));

		return arguments.stream();
	}

	/**
	 * 格式化版本字符串测试.
	 */
	@ParameterizedTest
	@MethodSource("formatVersionProvider")
	void formatVersion(String input, String result) {
		Assertions.assertEquals(result, Version.formatVersion(input));
	}

	@ParameterizedTest
	@MethodSource("parseProvider")
	void parse(String input, String result) {
		Assertions.assertEquals(result, String.valueOf(Version.parse(input)));
	}

	@Test
	void comparable() {
		List<Version> versions = Stream.of(Version.parse("2022.5"),
				Version.parse("2022.5.1"),
				Version.parse("2022.5.0"),
				Version.parse("SNAPSHOT"),
				Version.parse("2022.4"),
				Version.parse("2022.4.1"),
				Version.parse("2022.4.0"),
				Version.parse(null)).filter(Objects::nonNull).sorted().collect(Collectors.toList());

		List<Version> target = Arrays.asList(Version.parse("2022.4"),
				Version.parse("2022.4.0"),
				Version.parse("2022.4.1"),
				Version.parse("2022.5"),
				Version.parse("2022.5.0"),
				Version.parse("2022.5.1"));

		Assertions.assertEquals(target, versions);
	}

	@Test
	void empty() {
		Version version = Version.parse("4.0");
		Assertions.assertTrue(Version.notEmpty(version));
		Assertions.assertTrue(Version.notEmpty(version.safeChildren()));
		Assertions.assertTrue(Version.isEmpty(version.safeChildren().safeChildren()));
		Assertions.assertTrue(Version.isEmpty(version.safeChildren().children()));
	}
}
