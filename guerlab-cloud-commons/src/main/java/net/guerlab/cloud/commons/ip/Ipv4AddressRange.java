/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.ip;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static java.lang.Integer.parseInt;

/**
 * ipv4地址段
 *
 * @author guer
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
@Getter
@Slf4j
public class Ipv4AddressRange implements IpAddress, Ipv4 {

    /**
     * 类型
     */
    private IpType ipType;

    /**
     * 起止IP，左闭右闭[start, end]
     */
    private Ipv4Address start, end;

    /**
     * 子网掩码
     */
    private Ipv4Address subNetMask;

    private int mask;

    /**
     * 子网
     */
    private Ipv4Address subNet;

    /**
     * 原始字符串
     */
    private String rawString;

    public Ipv4AddressRange() {
        reset();
    }

    public Ipv4AddressRange(String ipRange) {
        reset();
        parseIpRange(ipRange);
    }

    protected static String toDecimalString(String inBinaryIpAddress) {
        StringBuilder decimalIp = new StringBuilder();

        for (int c = 0; c < 4; ++c) {
            String binary = inBinaryIpAddress.substring(c * 8, c * 8 + 8);
            int octet = parseInt(binary, 2);
            decimalIp.append(octet);
            if (c < 3) {
                decimalIp.append('.');
            }
        }

        return decimalIp.toString();
    }

    public void reset() {
        ipType = IpType.IPV4_SEGMENT;
        start = end = null;
        subNetMask = subNet = null;
        mask = 0;
        rawString = null;
    }

    /**
     * 分析IP地址，格式有4种，1.2.3.4,  1.2.3.4-12, 1.2.3.4-1.2.3.12, 1.2.3.4/32
     *
     * @param ipRange
     *         ip范围
     */
    protected void parseIpRange(String ipRange) {
        rawString = ipRange;
        ipRange = StringUtils.trimToNull(ipRange);
        if (ipRange == null) {
            throw new IllegalArgumentException("illegal ip ranges: " + rawString);
        }
        // 提取注释
        int s = ipRange.indexOf('-');
        if (s >= 0) {
            start = new Ipv4Address(StringUtils.trim(StringUtils.substring(ipRange, 0, s)));
            ipRange = StringUtils.trim(StringUtils.substring(ipRange, s + 1));
            if (!ipRange.contains(".")) {
                int tmpEnd = parseInt(ipRange);
                if (tmpEnd < 0 || tmpEnd > MAX_VALUE) {
                    throw new IllegalArgumentException("illegal ip ranges: " + rawString);
                }
                end = new Ipv4Address((start.getIpAddress() & 0x0FFFFFF00) + tmpEnd);
            } else {
                end = new Ipv4Address(ipRange);
            }

            return;
        }

        s = ipRange.indexOf("/");
        if (s > 0) {
            int prefix = parseInt(StringUtils.trim(StringUtils.substring(ipRange, s + 1)));
            if (prefix < 0 || prefix > 32) {
                throw new IllegalArgumentException("illegal arguments");
            }
            // 带有子网掩码
            mask = prefix;
            ipType = IpType.IPV4_SEGMENT_WITH_MASK;
            // 子网掩码
            subNetMask = computeMaskFromNetworkPrefix(prefix);
            Ipv4Address tmp = new Ipv4Address(StringUtils.trim(StringUtils.substring(ipRange, 0, s)));
            // 子网
            subNet = new Ipv4Address(subNetMask.getIpAddress() & tmp.getIpAddress());
            // 设置起止地址
            start = new Ipv4Address(subNet.getIpAddress());
            end = new Ipv4Address(subNet.getIpAddress() + (1L << (32 - prefix)) - 1);
            return;
        }

        start = new Ipv4Address(ipRange);
        end = new Ipv4Address(ipRange);

    }

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    protected Ipv4Address computeMaskFromNetworkPrefix(int prefix) {
        StringBuilder str = new StringBuilder();

        for (int decimalString = 0; decimalString < 32; ++decimalString) {
            if (decimalString < prefix) {
                str.append("1");
            } else {
                str.append("0");
            }
        }

        return new Ipv4Address(toDecimalString(str.toString()));
    }

    public boolean contains(IpAddress ipAddress) {
        switch (ipAddress.getIpType()) {
            case IPV4:
                return contains(start, end, (Ipv4Address) ipAddress);
            case IPV4_SEGMENT:
            case IPV4_SEGMENT_WITH_MASK:
                return contains(start, end, (Ipv4AddressRange) ipAddress);
            default:
                return false;
        }
    }

    private boolean contains(Ipv4Address s, Ipv4Address e, Ipv4Address item) {
        if (item == null) {
            return false;
        }
        long min = Math.min(s != null ? s.getIpAddress() : -1L, e != null ? e.getIpAddress() : -1L);
        long max = Math.max(s != null ? s.getIpAddress() : -1L, e != null ? e.getIpAddress() : -1L);
        return item.getIpAddress() >= min && item.getIpAddress() <= max;
    }

    private boolean contains(Ipv4Address s, Ipv4Address e, Ipv4AddressRange jiPv4AddressRange) {
        if (jiPv4AddressRange == null) {
            return false;
        }
        return contains(s, e, jiPv4AddressRange.getStart()) || contains(jiPv4AddressRange.getStart(),
                jiPv4AddressRange.getEnd(), s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ipv4AddressRange range = (Ipv4AddressRange) o;

        return Objects.equals(start, range.start) && Objects.equals(end, range.end);
    }

    @Override
    public int hashCode() {
        int result = 666;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
