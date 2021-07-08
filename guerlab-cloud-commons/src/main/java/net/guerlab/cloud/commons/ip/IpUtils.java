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

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * IP地址工具类
 *
 * @author guer
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class IpUtils {

    private static final String UNKNOWN = "unknown";

    private static final char SPLIT = ',';

    private static final String[] HEADERS = new String[] { "X-Forwarded-For", "Cdn-Src-Ip", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP", };

    private IpUtils() {

    }

    public static boolean inList(Collection<String> ips, String ip) {
        if (ips == null || ips.isEmpty() || ip == null) {
            return false;
        }

        IPv4Address address;
        try {
            address = new IPv4Address(ip);
        } catch (Exception e) {
            return false;
        }
        Collection<IPv4AddressRange> ranges = ips.stream().map(IpUtils::buildIPv4AddressRange).filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (ranges.isEmpty()) {
            return false;
        }

        final IPv4Address iPv4Address = address;

        return ips.stream().map(IpUtils::buildIPv4AddressRange).filter(Objects::nonNull)
                .anyMatch(range -> range.contains(iPv4Address));
    }

    private static IPv4AddressRange buildIPv4AddressRange(String ipRange) {
        try {
            return new IPv4AddressRange(ipRange);
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
}
