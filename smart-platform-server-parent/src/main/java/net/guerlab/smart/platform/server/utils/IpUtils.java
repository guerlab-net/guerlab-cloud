package net.guerlab.smart.platform.server.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

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
