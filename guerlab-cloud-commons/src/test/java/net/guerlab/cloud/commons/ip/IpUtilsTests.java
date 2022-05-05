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

package net.guerlab.cloud.commons.ip;

import java.util.Arrays;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * IpUtils测试.
 *
 * @author guer
 */
@Slf4j
class IpUtilsTests {

	@Test
	void range1() {
		Assertions.assertTrue(IpUtils.inList(Collections.singletonList("192.168.1.0/24"), "192.168.1.5"));
	}

	@Test
	void range2() {
		Assertions.assertTrue(IpUtils.inList(Collections.singletonList("192.168.1.1-192.168.3.10"), "192.168.2.5"));
	}

	@Test
	void range3() {
		Assertions.assertTrue(IpUtils.inList(Collections.singletonList("192.168.1.0-10"), "192.168.1.5"));
	}

	@Test
	void range4() {
		Assertions.assertTrue(
				IpUtils.inList(Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4", "192.168.1.5"),
						"192.168.1.5"));
	}

	@Test
	void mask() {
		Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.1/24");
		long mask = IpUtils.calculationIpv4Mask(ipv4.getStartAddress(), ipv4.getEndAddress());
		Assertions.assertEquals("11111111111111111111111100000000", Long.toString(mask, 2));
	}

}
