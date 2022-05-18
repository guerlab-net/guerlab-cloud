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
		from.setLongitude(new BigDecimal("104.075274"));
		from.setLatitude(new BigDecimal("30.654147"));

		to.setLongitude(new BigDecimal("104.059301"));
		to.setLatitude(new BigDecimal("30.671344"));
	}

	@Test
	void distance() {
		Assertions.assertEquals(new BigDecimal("2447.59"), GeoUtils.distance(from, to));
	}

	@Test
	void kilometerTest() {
		Assertions.assertEquals(new BigDecimal("2.45"), GeoUtils.distance(from, to, LengthUnit.KILOMETER));
	}

	@Test
	void metreTest() {
		Assertions.assertEquals(new BigDecimal("2447.59"), GeoUtils.distance(from, to, LengthUnit.METRE));
	}

	@Test
	void centimeterTest() {
		Assertions.assertEquals(new BigDecimal("244759.38"), GeoUtils.distance(from, to, LengthUnit.CENTIMETER));
	}

	@Test
	void toBytes() {
		GeoPoint point = new GeoPoint();
		point.setLongitude(new BigDecimal("116.510958"));
		point.setLatitude(new BigDecimal("39.90786"));

		byte[] bytes = new byte[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 75, -24, 46, -119, -77, 32, 93, 64, 92, -84, -88, -63, 52, -12, 67, 64};

		Assertions.assertArrayEquals(bytes, GeoUtils.toBytes(point));
	}

	@Test
	void toPoint() {
		byte[] bytes = new byte[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 75, -24, 46, -119, -77, 32, 93, 64, 92, -84, -88, -63, 52, -12, 67, 64};

		GeoPoint point = new GeoPoint();
		point.setSrid(0);
		point.setLongitude(new BigDecimal("116.510958"));
		point.setLatitude(new BigDecimal("39.90786"));

		Assertions.assertEquals(point, GeoUtils.toGeoPoint(bytes));
	}
}
