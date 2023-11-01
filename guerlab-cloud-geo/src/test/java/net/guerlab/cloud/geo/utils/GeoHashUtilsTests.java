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

package net.guerlab.cloud.geo.utils;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.geo.domain.GeoHash;
import net.guerlab.cloud.geo.domain.GeoPoint;

/**
 * @author guer
 */
class GeoHashUtilsTests {

	static final GeoPoint point = new GeoPoint();

	@BeforeAll
	static void before() {
		point.setLongitude(new BigDecimal("116.51095809414983"));
		point.setLatitude(new BigDecimal("39.90785996429622"));
	}

	@Test
	void encode() {
		Assertions.assertEquals("wx4g5308nh71", GeoHashUtils.encode(point));
	}

	@Test
	void decode() {
		Assertions.assertEquals(point, GeoHashUtils.decode("wx4g5308nh71"));
	}

	@Test
	void getGeoHashExpand() {
		GeoHash geoHash = GeoHashUtils.getGeoHashExpand("wx4g5308nh71", 12);
		Assertions.assertEquals("wx4g5308nh6f", geoHash.getTopLeft());
		Assertions.assertEquals("wx4g5308nh6c", geoHash.getTop());
		Assertions.assertEquals("wx4g5308nh6b", geoHash.getTopRight());
		Assertions.assertEquals("wx4g5308nh74", geoHash.getLeft());
		Assertions.assertEquals("wx4g5308nh71", geoHash.getCenter());
		Assertions.assertEquals("wx4g5308nh70", geoHash.getRight());
		Assertions.assertEquals("wx4g5308nh76", geoHash.getBottomLeft());
		Assertions.assertEquals("wx4g5308nh73", geoHash.getBottom());
		Assertions.assertEquals("wx4g5308nh72", geoHash.getBottomRight());
	}
}
