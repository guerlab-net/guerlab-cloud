package net.guerlab.cloud.web.core.request;

import org.springframework.lang.Nullable;

/**
 * 请求上下文保持
 *
 * @author guer
 */
public interface RequestContextHolder {

    /**
     * 获取请求方法
     *
     * @return 请求方法
     */
    @Nullable
    String getRequestMethod();

    /**
     * 获取请求路径
     *
     * @return 请求路径
     */
    @Nullable
    String getRequestPath();
}
