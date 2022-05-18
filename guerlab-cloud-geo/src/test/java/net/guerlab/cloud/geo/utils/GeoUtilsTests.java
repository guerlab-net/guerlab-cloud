/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.geo.utils;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.geo.domain.GeoPoint;
import net.guerlab.cloud.geo.enums.LengthUnit;

/**
 * @author guer
 */
class GeoUtilsTests {

	static GeoPoint from = new GeoPoint();
	static GeoPoint to = new GeoPoint();

	@BeforeAll
	static void before() {
		from.setLongitude(new BigDecimal("116.510958"));
		from.setLatitude(new BigDecimal("39.90786"));

		to.setLongitude(new BigDecimal("116.510842"));
		to.setLatitude(new BigDecimal("39.90777"));
	}

	@Test
	void distance() {
		Assertions.assertEquals(new BigDecimal("14.07"), GeoUtils.distance(from, to));
	}

	@Test
	void kilometerTest() {
		Assertions.assertEquals(new BigDecimal("14.07"), GeoUtils.distance(from, to, LengthUnit.KILOMETER));
	}

	@Test
	void metreTest() {
		Assertions.assertEquals(new BigDecimal("14072.86"), GeoUtils.distance(from, to, LengthUnit.METRE));
	}

	@Test
	void centimeterTest() {
		Assertions.assertEquals(new BigDecimal("1407285.71"), GeoUtils.distance(from, to, LengthUnit.CENTIMETER));
	}
}
