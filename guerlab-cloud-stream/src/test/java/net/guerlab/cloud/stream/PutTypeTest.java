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

package net.guerlab.cloud.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 推送类型格式化名称测试.
 *
 * @author guer
 */
class PutTypeTest {

	@Test
	void input1() {
		Assertions.assertEquals("onTest-in-0", PutType.IN.formatName("test"));
	}

	@Test
	void input2() {
		Assertions.assertEquals("onTest-in-0", PutType.IN.formatName("onTest"));
	}

	@Test
	void output() {
		Assertions.assertEquals("test-out-0", PutType.OUT.formatName("test"));
	}
}
