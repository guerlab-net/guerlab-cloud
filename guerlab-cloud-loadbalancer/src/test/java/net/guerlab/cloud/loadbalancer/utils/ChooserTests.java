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

package net.guerlab.cloud.loadbalancer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Chooser测试.
 *
 * @author guer
 */
class ChooserTests {

	@Test
	void random() {
		List<Pair<String>> instancesWithWeight = new ArrayList<>();
		instancesWithWeight.add(new Pair<>("a", 0.5));
		instancesWithWeight.add(new Pair<>("b", 1.0));
		Chooser<String, String> chooser = new Chooser<>("random");
		chooser.refresh(instancesWithWeight);

		for (int i = 0; i < 4; i++) {
			String val = chooser.random();
			Assertions.assertTrue(Objects.equals(val, "a") || Objects.equals(val, "b"));
		}
	}

	@Test
	void randomWithWeight() {
		List<Pair<String>> instancesWithWeight = new ArrayList<>();
		instancesWithWeight.add(new Pair<>("a", 0.5));
		instancesWithWeight.add(new Pair<>("b", 1.0));
		Chooser<String, String> chooser = new Chooser<>("randomWithWeight");
		chooser.refresh(instancesWithWeight);

		int aSize = 0;
		int bSize = 0;

		for (int i = 0; i < 10; i++) {
			String val = chooser.randomWithWeight();
			if ("a".equals(val)) {
				aSize++;
			}
			else {
				bSize++;
			}
		}

		Assertions.assertEquals(4, aSize);
		Assertions.assertEquals(6, bSize);
	}
}
