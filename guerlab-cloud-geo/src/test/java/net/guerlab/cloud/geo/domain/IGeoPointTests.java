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

package net.guerlab.cloud.geo.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author guer
 */
class IGeoPointTests {

	@Test
	void isEmpty() {
		GeoPoint point = new GeoPoint();
		Assertions.assertTrue(point.isEmpty());
	}

	@Test
	void notEmpty() {
		GeoPoint point = new GeoPoint();
		point.setLongitude(new BigDecimal("116.51095809414983"));
		point.setLatitude(new BigDecimal("39.90785996429622"));
		Assertions.assertFalse(point.isEmpty());
	}
}
