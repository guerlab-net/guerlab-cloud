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

package net.guerlab.cloud.commons.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.commons.enums.Cycle;

/**
 * @author guer
 */
class DateScopeTests {

	@Test
	void dateOffsetAndCycle() {
		DateScope scope = new DateScope();
		scope.setDateOffset(-1L);
		scope.setCycle(Cycle.NEARLY_ONE_MONTH);

		LocalDate now = LocalDate.now();
		LocalDate endDate = now.plusDays(-1);
		LocalDate startDate = endDate.plusMonths(-1);

		DateScope.RangeResult rangeResult = scope.getRangeResult();

		Assertions.assertEquals(startDate, rangeResult.getStartDate());
		Assertions.assertEquals(endDate, rangeResult.getEndDate());
	}

	@Test
	void dateOffsetAndDays() {
		DateScope scope = new DateScope();
		scope.setDateOffset(-1L);
		scope.setDays(30L);

		LocalDate now = LocalDate.now();
		LocalDate endDate = now.plusDays(-1);
		LocalDate startDate = endDate.plusDays(-30);

		DateScope.RangeResult rangeResult = scope.getRangeResult();

		Assertions.assertEquals(startDate, rangeResult.getStartDate());
		Assertions.assertEquals(endDate, rangeResult.getEndDate());
	}
}
