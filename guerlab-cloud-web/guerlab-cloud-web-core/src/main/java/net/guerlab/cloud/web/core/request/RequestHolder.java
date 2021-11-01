package net.guerlab.cloud.web.core.request;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * 请求保持
 *
 * @author guer
 */
public class RequestHolder {

    private static final Collection<RequestContextHolder> HOLDERS;

    static {
        HOLDERS = ServiceLoader.load(RequestContextHolder.class).stream().map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    private RequestHolder() {

    }

    /**
     * 获取请求方法
     *
     * @return 请求方法
     * @throws NullPointerException
     *         当获取失败的时候抛出NullPointerException
     */
    public static String getRequestMethod() {
        String requestMethod = requestMethod();

        if (requestMethod == null) {
            throw new NullPointerException("requestMethod is null");
        }

        return requestMethod;
    }

    /**
     * 获取请求方法
     *
     * @return 请求方法
     */
    @Nullable
    public static String requestMethod() {
        return HOLDERS.stream().map(RequestContextHolder::getRequestMethod).filter(Objects::nonNull).findFirst()
                .orElse(null);
    }

    /**
     * 获取请求路径
     *
     * @return 请求路径
     * @throws NullPointerException
     *         当获取失败的时候抛出NullPointerException
     */
    public static String getRequestPath() {
        String requestPath = requestPath();

        if (requestPath == null) {
            throw new NullPointerException("requestPath is null");
        }

        return requestPath;
    }

    /**
     * 获取请求路径
     *
     * @return 请求路径
     */
    @Nullable
    public static String requestPath() {
        return HOLDERS.stream().map(RequestContextHolder::getRequestPath).filter(Objects::nonNull).findFirst()
                .orElse(null);
    }
}
