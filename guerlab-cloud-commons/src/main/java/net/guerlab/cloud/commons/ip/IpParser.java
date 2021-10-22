package net.guerlab.cloud.commons.ip;

import org.springframework.lang.Nullable;

/**
 * ip地址解析器
 *
 * @author guer
 */
public interface IpParser {

    /**
     * 判断是否支持对该请求对象的处理
     *
     * @param request
     *         请求对象
     * @return 是否允许处理
     */
    boolean accept(Object request);

    /**
     * 从请求头获取IP地址
     *
     * @param request
     *         请求对象
     * @param headerName
     *         请求头名称
     * @return IP地址
     */
    @Nullable
    String getIpByHeader(Object request, String headerName);

    /**
     * 从远端地址获取IP地址
     *
     * @param request
     *         请求对象
     * @return IP地址
     */
    @Nullable
    String getIpByRemoteAddress(Object request);
}
