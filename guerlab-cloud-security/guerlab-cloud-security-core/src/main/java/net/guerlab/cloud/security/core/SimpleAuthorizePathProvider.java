package net.guerlab.cloud.security.core;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 简单授权路径提供者
 *
 * @author guer
 */
public class SimpleAuthorizePathProvider implements AuthorizePathProvider {

    private final List<String> paths;

    private HttpMethod httpMethod;

    /**
     * 根据路径列表构造授权路径提供者
     *
     * @param paths
     *         路径列表
     */
    public SimpleAuthorizePathProvider(List<String> paths) {
        this.paths = paths;
    }

    /**
     * 根据路径列表构造授权路径提供者
     *
     * @param httpMethod
     *         请求方法
     * @param paths
     *         路径列表
     */
    public SimpleAuthorizePathProvider(HttpMethod httpMethod, List<String> paths) {
        this.httpMethod = httpMethod;
        this.paths = paths;
    }

    @Nullable
    @Override
    public HttpMethod httpMethod() {
        return httpMethod;
    }

    @Override
    public List<String> paths() {
        return paths;
    }
}
