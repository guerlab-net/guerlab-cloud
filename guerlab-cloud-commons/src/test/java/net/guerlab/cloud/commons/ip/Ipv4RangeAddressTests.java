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

package net.guerlab.cloud.commons.ip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * ipv4地址段测试.
 *
 * @author guer
 */
class Ipv4RangeAddressTests {

	@Test
	void parseIpv4WithRangeLinkFlag() {
		String startAddress = "192.168.1.1";
		String endAddress = "192.168.2.255";
		Ipv4 ipv4 = Ipv4Utils.parseIpv4WithRangeLinkFlag(startAddress + "-" + endAddress);
		Assertions.assertEquals(IpUtils.parseIpv4Address(startAddress), ipv4.getStartAddress());
		Assertions.assertEquals(IpUtils.parseIpv4Address(endAddress), ipv4.getEndAddress());
	}

	@ParameterizedTest
	@ValueSource(strings = {"192.168.1.1-252", "192.168.1.1-2.255", "192.168.1.1-170.2.255",
			"192.168.1.1-193.255.255.255", "192.168.1.1/20-192.170.101.1/20", "192.168.1.152/27"})
	void parse(String address) {
		Ipv4 ipv4 = IpUtils.parseIpv4(address);
		Assertions.assertNotNull(ipv4);
		System.out.println(ipv4);
	}

	@Test
	void rangeCheck() {
		Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.152/27");
		Assertions.assertEquals(ipv4.getStartAddress(), IpUtils.parseIpv4("192.168.1.128").getStartAddress());
		Assertions.assertEquals(ipv4.getEndAddress(), IpUtils.parseIpv4("192.168.1.159").getStartAddress());
	}
}
