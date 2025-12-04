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

package net.guerlab.cloud.commons.util;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.commons.entity.EnumDescription;
import net.guerlab.cloud.commons.enums.EnumDescriptionSupport;

/**
 * @author guer
 */
class EnumDescriptionUtilsTests {

	@Test
	void getDescriptionsTest() {
		List<EnumDescription> target = new ArrayList<>();
		target.add(new EnumDescription("A", "this is a"));
		target.add(new EnumDescription("B", "this is b"));

		Assertions.assertEquals(target, EnumDescriptionUtils.getDescriptions(TestEnum.class.getName()));
	}

	@Getter
	@AllArgsConstructor
	public enum TestEnum implements EnumDescriptionSupport {
		A("this is a"),
		B("this is b");

		private final String description;
	}
}
