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
