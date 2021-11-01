package net.guerlab.cloud.web.webflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 请求工具类
 *
 * @author guer
 */
@Slf4j
public class RequestUtils {

    private RequestUtils() {

    }

    /**
     * 解析请求路径
     *
     * @param request
     *         请求对象
     * @return 请求路径
     */
    public static String parseRequestUri(ServerHttpRequest request) {
        return parseRequestUri0(request.getPath());
    }

    /**
     * 解析请求路径
     *
     * @param request
     *         请求对象
     * @return 请求路径
     */
    public static String parseRequestUri(ServerRequest request) {
        return parseRequestUri0(request.requestPath());
    }

    private static String parseRequestUri0(RequestPath requestPath) {
        String contextPath = StringUtils.trimToNull(requestPath.contextPath().value());
        String requestUri = requestPath.value();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }
}
