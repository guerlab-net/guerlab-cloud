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

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.commons.ip.IpUtils;

/**
 * ipv4地址测试.
 *
 * @author guer
 */
class Ipv4AddressTests {

	@Test
	void parseTest() {
		long longAddress = 3232235781L;
		String stringAddress = "192.168.1.5";
		Assertions.assertEquals(IpUtils.parseIpv4(longAddress), IpUtils.parseIpv4(stringAddress));
	}

	@Test
	void convertStringTest() {
		Assertions.assertEquals("192.168.1.5", IpUtils.convertIpv4String(3232235781L));
	}

	@Test
	void hashTest() {
		Assertions.assertEquals(Objects.hashCode(3232235781L), IpUtils.parseIpv4("192.168.1.5").hashCode());
	}
}
