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

package net.guerlab.cloud.web.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.web.core.data.DataHandler;

/**
 * @author guer
 */
class DataAccessUtilsTests {

	@Test
	void iterable() {
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("key", "oldVal");
		list.add(map);

		DataAccessUtils.objectHandler("key", list, new TestDataHandler());

		Assertions.assertEquals("transformation-oldVal", list.get(0).get("key"));
	}

	@Test
	void array() {
		Map<String, Object> map = new HashMap<>();
		map.put("key", "oldVal");
		Map<?, ?>[] array = new Map[] {map};

		DataAccessUtils.objectHandler("key", array, new TestDataHandler());

		Assertions.assertEquals("transformation-oldVal", array[0].get("key"));
	}

	@Test
	void map() {
		Map<String, Object> map = new HashMap<>();
		map.put("key", "oldVal");

		DataAccessUtils.objectHandler("key", map, new TestDataHandler());

		Assertions.assertEquals("transformation-oldVal", map.get("key"));
	}

	@Test
	void object() {
		TestObj object = new TestObj();
		object.setKey("oldVal");

		DataAccessUtils.objectHandler("key", object, new TestDataHandler());

		Assertions.assertEquals("transformation-oldVal", object.getKey());
	}

	@Data
	private static class TestObj {

		private String key;
	}

	private static class TestDataHandler implements DataHandler {

		@Override
		public Object transformation(Object value) {
			return "transformation-" + value;
		}
	}
}
