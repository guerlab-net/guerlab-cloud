package net.guerlab.cloud.security.core;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 授权路径提供者
 *
 * @author guer
 */
public interface AuthorizePathProvider {

    /**
     * 请求方式
     *
     * @return 请求方式
     */
    @Nullable
    HttpMethod httpMethod();

    /**
     * 路径列表
     *
     * @return 路径列表
     */
    List<String> paths();
}
