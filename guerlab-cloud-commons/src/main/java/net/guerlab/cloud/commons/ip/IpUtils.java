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

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * IP地址工具类
 *
 * @author guer
 */
public class IpUtils {

    private static final String UNKNOWN = "unknown";

    private static final char SPLIT = ',';

    private static final String[] HEADERS = new String[] { "X-Forwarded-For", "Cdn-Src-Ip", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP", };

    private IpUtils() {

    }

    /**
     * 判断某个IP是否在指定的IP范围中
     *
     * @param ips
     *         IP范围
     * @param targetIp
     *         目标IP
     * @return 是否在指定的IP范围中
     */
    public static boolean inList(@Nullable Collection<String> ips, @Nullable String targetIp) {
        if (ips == null || ips.isEmpty() || targetIp == null) {
            return false;
        }

        Ipv4Address target;
        try {
            target = new Ipv4Address(targetIp);
        } catch (Exception e) {
            return false;
        }
        Collection<Ipv4> ranges = ips.stream().map(ip -> {
            try {
                return parseIpv4(ip);
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if (ranges.isEmpty()) {
            return false;
        }

        return ranges.stream().anyMatch(range -> range.contains(target));
    }

    /**
     * 获取请求的ip地址
     *
     * @param request
     *         请求
     * @return ip地址
     */
    @SuppressWarnings("unused")
    public static String getIp(HttpServletRequest request) {
        for (String headerName : HEADERS) {
            String ip = getIpByHeader(request, headerName);
            if (ip != null) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 通过请求头获取请求的ip地址
     *
     * @param request
     *         请求
     * @param headerName
     *         请求头名称
     * @return ip地址
     */
    @Nullable
    private static String getIpByHeader(HttpServletRequest request, String headerName) {
        String value = request.getHeader(headerName);
        if (isNull(value)) {
            return null;
        }
        int index = value.indexOf(SPLIT);
        if (index != -1) {
            return value.substring(0, index).trim();
        } else {
            return value;
        }
    }

    private static boolean isNull(String ip) {
        return StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }

    /**
     * 解析ipv4地址
     *
     * @param address
     *         IP地址
     * @return IP地址
     */
    public static Ipv4 parseIpv4(long address) {
        return new Ipv4Address(address);
    }

    /**
     * 解析ipv4地址
     *
     * @param address
     *         IP地址
     * @return IP地址
     */
    public static Ipv4 parseIpv4(String address) {
        if ((address = StringUtils.trimToNull(address)) == null) {
            throw new IllegalArgumentException("address invalid");
        }

        boolean hasRangeLinkFlag = address.contains(Ipv4.RANGE_LINK_FLAG);
        boolean hasMaskFlag = address.contains(Ipv4.MASK_FLAG);

        if (hasRangeLinkFlag && hasMaskFlag) {
            // support format: 1.2.3.4/16-1.3.3.4/16
            String[] addressArray = address.split(Ipv4.RANGE_LINK_FLAG);
            if (addressArray.length != 2) {
                throw new IllegalArgumentException("address is invalid");
            }
            Ipv4 start = parseIpv4(addressArray[0]);
            Ipv4 end = parseIpv4(addressArray[1]);
            return new Ipv4RangeAddress(start.getStartAddress(), end.getEndAddress());
        } else if (hasRangeLinkFlag) {
            // support format: 1.2.3.4-255; 1.2.3.4-1.2.4.5
            return parseWithRangeLinkFlag(address);
        } else if (hasMaskFlag) {
            // support format: 1.2.3.4/32
            return parseWithMaskFlag(address);
        } else {
            return new Ipv4Address(address);
        }
    }

    /**
     * 基于范围链接符标志的解析
     *
     * @param ipRange
     *         ip范围
     * @return ipv4地址段
     */
    public static Ipv4RangeAddress parseWithRangeLinkFlag(String ipRange) {
        String[] ipRangeArray = ipRange.split(Ipv4.RANGE_LINK_FLAG, 2);
        long start = parseIpv4Address(ipRangeArray[0]);

        String[] groupValues = ipRangeArray[1].split(Ipv4.SEPARATOR_REG, Ipv4.GROUP_SIZE);
        int length = groupValues.length;
        long baseAddress = start & (0x0FFFFFFFFL << (length * 8));
        int offset = 0;
        while (offset < length) {
            baseAddress += parseIpv4GroupValue(groupValues[offset]) << ((length - offset - 1) * 8);
            offset++;
        }

        return new Ipv4RangeAddress(start, baseAddress);
    }

    /**
     * 基于子网掩码标志的解析
     *
     * @param ipRange
     *         ip范围
     * @return ipv4地址段
     */
    public static Ipv4RangeAddress parseWithMaskFlag(String ipRange) {
        String[] ipRangeArray = ipRange.split(Ipv4.MASK_FLAG, 2);
        int maskLength = Integer.parseInt(ipRangeArray[1]);
        if (maskLength < 0 || maskLength > Ipv4.MAX_MASK) {
            throw new IllegalArgumentException("illegal arguments");
        }
        long tmp = parseIpv4Address(ipRangeArray[0]);
        // 子网掩码
        Ipv4Address subNetMask = IpUtils.computeMaskFromNetworkPrefix(maskLength);
        // 子网
        Ipv4Address subNet = new Ipv4Address(subNetMask.getIpAddress() & tmp);
        // 设置起止地址
        Ipv4Address start = new Ipv4Address(subNet.getIpAddress());
        Ipv4Address end = new Ipv4Address(subNet.getIpAddress() + (0x00000001L << (Ipv4.MAX_MASK - maskLength)) - 1);

        return new Ipv4RangeAddress(start.getStartAddress(), end.getIpAddress(), subNetMask.getIpAddress(), maskLength);
    }

    /**
     * 解析ipv4分组地址
     *
     * @param str
     *         ipv4分组地址
     * @return 地址值
     */
    private static long parseIpv4GroupValue(String str) {
        try {
            long val = Long.parseLong(str);
            if (val < 0 || val > Ipv4.MAX_VALUE) {
                throw new IllegalArgumentException(str + " is invalid");
            }
            return val;
        } catch (Exception e) {
            IllegalArgumentException error = new IllegalArgumentException(str + " is invalid");
            error.addSuppressed(e);
            throw error;
        }
    }

    /**
     * 通过掩码长度构造IPv4掩码地址
     *
     * @param prefix
     *         掩码长度
     * @return IPv4掩码地址
     */
    public static Ipv4Address computeMaskFromNetworkPrefix(int prefix) {
        String str = "1".repeat(prefix) + "0".repeat(32 - prefix);
        return new Ipv4Address(Long.parseLong(str, 2));
    }

    /**
     * 通过起始地址和结束地址构造IPv4掩码地址
     *
     * @param startAddress
     *         起始地址
     * @param endAddress
     *         结束地址
     * @return IPv4掩码地址
     */
    public static long calculationMask(long startAddress, long endAddress) {
        long mask = 0L;
        for (int i = 0; i < Ipv4.GROUP_SIZE; i++) {
            mask += (~((startAddress >> (i * 8) & 0xFF) ^ (endAddress >> (i * 8) & 0xFF)) & 0x0FF) << (i * 8);
        }
        return mask;
    }

    /**
     * 将数值类型地址转变为字符串格式地址
     *
     * @param ipAddress
     *         数值类型地址
     * @return 字符串格式地址
     */
    public static String convertIpv4String(long ipAddress) {
        return String.format(Ipv4.FORMAT, ipAddress >> 24 & 255, ipAddress >> 16 & 255, ipAddress >> 8 & 255,
                ipAddress & 255);
    }

    /**
     * 解析IPv4字符串格式地址
     *
     * @param ipAddressStr
     *         字符串格式地址
     * @return 数值类型地址
     */
    public static long parseIpv4Address(String ipAddressStr) {
        ipAddressStr = StringUtils.trimToNull(ipAddressStr);
        if (ipAddressStr == null) {
            throw new IllegalArgumentException();
        }

        long offset = 24;
        long result = 0;
        for (String valStr : ipAddressStr.split(Ipv4.SEPARATOR_REG, Ipv4.GROUP_SIZE)) {
            long val;
            try {
                val = Long.parseLong(valStr);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
            }
            if (val < 0 || val > Ipv4.MAX_VALUE) {
                throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
            }

            result += val << offset;
            offset -= 8;
        }

        return result;
    }
}
