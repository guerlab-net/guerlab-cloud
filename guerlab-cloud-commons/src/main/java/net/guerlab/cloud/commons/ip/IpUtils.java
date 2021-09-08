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

        IpSingleAddress target = parseIpSingleAddress(targetIp);
        if (target == null) {
            return false;
        }

        Collection<IpAddress> ranges = ips.stream().map(IpUtils::parseIpRangeAddress).filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (ranges.isEmpty()) {
            return false;
        }

        return ranges.stream().anyMatch(range -> range.contains(target));
    }

    @Nullable
    private static IpSingleAddress parseIpSingleAddress(String ip) {
        try {
            if (Ipv4Utils.isIpv4(ip)) {
                return new Ipv4Address(ip);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    private static IpAddress parseIpRangeAddress(String ip) {
        try {
            if (Ipv4Utils.isIpv4(ip)) {
                return parseIpv4(ip);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
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
        return Ipv4Utils.parseIpv4(address);
    }

    /**
     * 解析ipv4地址
     *
     * @param address
     *         IP地址
     * @return IP地址
     */
    public static Ipv4 parseIpv4(String address) {
        return Ipv4Utils.parseIpv4(address);
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
    public static long calculationIpv4Mask(long startAddress, long endAddress) {
        return Ipv4Utils.calculationIpv4Mask(startAddress, endAddress);
    }

    /**
     * 将数值类型地址转变为字符串格式地址
     *
     * @param ipAddress
     *         数值类型地址
     * @return 字符串格式地址
     */
    public static String convertIpv4String(long ipAddress) {
        return Ipv4Utils.convertIpv4String(ipAddress);
    }

    /**
     * 解析IPv4字符串格式地址
     *
     * @param ipAddressStr
     *         字符串格式地址
     * @return 数值类型地址
     */
    public static long parseIpv4Address(String ipAddressStr) {
        return Ipv4Utils.parseIpv4Address(ipAddressStr);
    }
}
