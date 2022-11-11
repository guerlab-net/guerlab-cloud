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

package net.guerlab.cloud.commons.enums;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author guer
 */
class CycleEnumTests {

	private static final LocalDateTime BASE = LocalDateTime.of(2022, 10, 15, 13, 14, 16);

	static Stream<Arguments> testSourceProvider() {
		List<Arguments> arguments = new ArrayList<>();
		arguments.add(Arguments.arguments(Cycle.YEAR, LocalDateTime.of(2022, 1, 1, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.HALF_YEAR, LocalDateTime.of(2022, 7, 1, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.QUARTER, LocalDateTime.of(2022, 10, 1, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.MONTH, LocalDateTime.of(2022, 10, 1, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.WEEK, LocalDateTime.of(2022, 10, 10, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.DAY, LocalDateTime.of(2022, 10, 15, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_30_DAYS, LocalDateTime.of(2022, 9, 15, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_60_DAYS, LocalDateTime.of(2022, 8, 16, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_90_DAYS, LocalDateTime.of(2022, 7, 17, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_ONE_MONTH, LocalDateTime.of(2022, 9, 15, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_TWO_MONTH, LocalDateTime.of(2022, 8, 15, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_THREE_MONTH, LocalDateTime.of(2022, 7, 15, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_HALF_YEAR, LocalDateTime.of(2022, 4, 15, 13, 14, 16)));
		arguments.add(Arguments.arguments(Cycle.NEARLY_ONE_YEAR, LocalDateTime.of(2021, 10, 15, 13, 14, 16)));
		return arguments.stream();
	}

	@ParameterizedTest
	@MethodSource("testSourceProvider")
	void time(Cycle cycle, LocalDateTime startTime) {
		Assertions.assertEquals(startTime, cycle.startTime(BASE, false));
	}
}
