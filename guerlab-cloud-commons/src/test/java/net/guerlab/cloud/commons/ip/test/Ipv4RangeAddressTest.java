package net.guerlab.cloud.commons.ip.test;

import net.guerlab.cloud.commons.ip.IpUtils;
import net.guerlab.cloud.commons.ip.Ipv4;
import net.guerlab.cloud.commons.ip.Ipv4Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ipv4地址段测试
 *
 * @author guer
 */
public class Ipv4RangeAddressTest {

    @Test
    public void parse0() {
        String startAddress = "192.168.1.1";
        String endAddress = "192.168.2.255";
        Ipv4 ipv4 = Ipv4Utils.parseIpv4WithRangeLinkFlag(startAddress + "-" + endAddress);
        Assertions.assertEquals(IpUtils.parseIpv4Address(startAddress), ipv4.getStartAddress());
        Assertions.assertEquals(IpUtils.parseIpv4Address(endAddress), ipv4.getEndAddress());
    }

    @Test
    public void parse1() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.1-252");
        Assertions.assertNotNull(ipv4);
        System.out.println(ipv4);
    }

    @Test
    public void parse2() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.1-2.255");
        Assertions.assertNotNull(ipv4);
        System.out.println(ipv4);
    }

    @Test
    public void parse3() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.1-170.2.255");
        Assertions.assertNotNull(ipv4);
        System.out.println(ipv4);
    }

    @Test
    public void parse4() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.1-193.255.255.255");
        Assertions.assertNotNull(ipv4);
        System.out.println(ipv4);
    }

    @Test
    public void parse5() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.1/20-192.170.101.1/20");
        Assertions.assertNotNull(ipv4);
        System.out.println(ipv4);
    }

    @Test
    public void parse6() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.152/27");
        Assertions.assertNotNull(ipv4);
        System.out.println(ipv4);
    }

    @Test
    public void parse7() {
        Ipv4 ipv4 = IpUtils.parseIpv4("192.168.1.152/27");
        Assertions.assertEquals(ipv4.getStartAddress(), IpUtils.parseIpv4("192.168.1.128").getStartAddress());
        Assertions.assertEquals(ipv4.getEndAddress(), IpUtils.parseIpv4("192.168.1.159").getStartAddress());
    }
}
