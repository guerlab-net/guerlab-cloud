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
package net.guerlab.cloud.commons.ip.test;

import net.guerlab.cloud.commons.ip.IpType;
import net.guerlab.cloud.commons.ip.IpUtils;
import net.guerlab.cloud.commons.ip.Ipv4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ipv4地址类型测试
 *
 * @author guer
 */
public class Ipv4AddressTypeTest {

    @Test
    public void single() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1");
        Assertions.assertTrue(IpType.isIpv4(ipv4.getIpType()));
        Assertions.assertFalse(IpType.isIpv6(ipv4.getIpType()));
        Assertions.assertTrue(IpType.isSingleIp(ipv4.getIpType()));
        Assertions.assertFalse(IpType.isIpSegment(ipv4.getIpType()));
    }

    @Test
    public void segment() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1/20");
        Assertions.assertTrue(IpType.isIpv4(ipv4.getIpType()));
        Assertions.assertFalse(IpType.isIpv6(ipv4.getIpType()));
        Assertions.assertFalse(IpType.isSingleIp(ipv4.getIpType()));
        Assertions.assertTrue(IpType.isIpSegment(ipv4.getIpType()));
    }
}
