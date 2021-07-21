package net.guerlab.cloud.commons.ip.test;

import net.guerlab.cloud.commons.ip.Ipv4Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ipv4地址测试
 *
 * @author guer
 */
public class Ipv4AddressTest {

    @Test
    public void parseTest() {
        long longAddress = Long.parseLong("3232235781");
        String stringAddress = "192.168.1.5";
        Assertions.assertEquals(new Ipv4Address(longAddress), new Ipv4Address(stringAddress));
    }
}
