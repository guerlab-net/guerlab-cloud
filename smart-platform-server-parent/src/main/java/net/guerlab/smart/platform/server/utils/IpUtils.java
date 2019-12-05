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

    private static final String HEADER_CDN_SRC_IP = "Cdn-Src-Ip";

    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";

    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    private static final String HEADER_HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

    private static final String HEADER_HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    private static final String HEADER_X_REAL_IP = "X-Real-IP";

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
        String ip = request.getHeader(HEADER_CDN_SRC_IP);
        if (isNull(ip)) {
            String ips = request.getHeader(HEADER_X_FORWARDED_FOR);
            if (!isNull(ips)) {
                int index = ips.indexOf(',');
                if (index != -1) {
                    ip = ips.substring(index + 1).trim();
                } else {
                    ip = ips;
                }
            }
        }
        if (isNull(ip)) {
            ip = request.getHeader(HEADER_PROXY_CLIENT_IP);
        }
        if (isNull(ip)) {
            ip = request.getHeader(HEADER_WL_PROXY_CLIENT_IP);
        }
        if (isNull(ip)) {
            ip = request.getHeader(HEADER_HTTP_CLIENT_IP);
        }
        if (isNull(ip)) {
            ip = request.getHeader(HEADER_HTTP_X_FORWARDED_FOR);
        }
        if (isNull(ip)) {
            ip = request.getHeader(HEADER_X_REAL_IP);
        }
        if (isNull(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private static boolean isNull(String ip) {
        return StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }
}
