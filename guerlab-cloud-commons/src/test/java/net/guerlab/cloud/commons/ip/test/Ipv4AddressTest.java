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

import net.guerlab.cloud.commons.ip.IpUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * ipv4地址测试
 *
 * @author guer
 */
public class Ipv4AddressTest {

    @Test
    public void parseTest() {
        long longAddress = 3232235781L;
        String stringAddress = "192.168.1.5";
        Assertions.assertEquals(IpUtils.parseIpv4(longAddress), IpUtils.parseIpv4(stringAddress));
    }

    @Test
    public void convertStringTest() {
        Assertions.assertEquals("192.168.1.5", IpUtils.convertIpv4String(3232235781L));
    }

    @Test
    public void hashTest() {
        Assertions.assertEquals(Objects.hashCode(3232235781L), IpUtils.parseIpv4("192.168.1.5").hashCode());
    }
}
