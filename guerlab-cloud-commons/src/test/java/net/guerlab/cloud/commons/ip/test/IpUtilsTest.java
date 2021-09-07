package net.guerlab.cloud.commons.ip.test;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.commons.ip.IpUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * IpUtils测试
 *
 * @author guer
 */
@Slf4j
public class IpUtilsTest {

    @Test
    public void range1() {
        Assertions.assertTrue(IpUtils.inList(Collections.singletonList("192.168.1.0/24"), "192.168.1.5"));
    }

    @Test
    public void range2() {
        Assertions.assertTrue(IpUtils.inList(Collections.singletonList("192.168.1.1-192.168.3.10"), "192.168.2.5"));
    }

    @Test
    public void range3() {
        Assertions.assertTrue(IpUtils.inList(Collections.singletonList("192.168.1.0-10"), "192.168.1.5"));
    }

    @Test
    public void range4() {
        Assertions.assertTrue(
                IpUtils.inList(Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4", "192.168.1.5"),
                        "192.168.1.5"));
    }

    @Test
    public void mask() {
        long mask = IpUtils
                .calculationMask(IpUtils.parseIpv4Address("192.168.1.1"), IpUtils.parseIpv4Address("192.168.1.254"));
        Assertions.assertEquals("11111111111111111111111100000000", Long.toString(mask, 2));
    }

}
