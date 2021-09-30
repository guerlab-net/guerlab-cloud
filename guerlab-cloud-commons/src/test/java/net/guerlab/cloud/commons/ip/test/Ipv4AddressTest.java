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
