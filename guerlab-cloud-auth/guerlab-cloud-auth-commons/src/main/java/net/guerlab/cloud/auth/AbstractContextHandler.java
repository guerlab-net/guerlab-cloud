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
package net.guerlab.cloud.auth;

import net.guerlab.cloud.commons.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象上下文处理器
 *
 * @author guer
 */
@SuppressWarnings("unused")
public abstract class AbstractContextHandler {

    private final static InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL = new InheritableThreadLocal<>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(16);
        }
    };

    /**
     * 设置内容
     *
     * @param key
     *         key
     * @param value
     *         内容
     */
    @SuppressWarnings("SameParameterValue")
    protected static void set(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }

    /**
     * 获取内容
     *
     * @param key
     *         key
     * @param <T>
     *         值类型
     * @return 内容
     */
    @SuppressWarnings({ "unchecked", "SameParameterValue" })
    protected static <T> T get(String key) {
        return (T) THREAD_LOCAL.get().get(key);
    }

    /**
     * 清除当前内容
     */
    public static void clean() {
        THREAD_LOCAL.remove();
    }

    /**
     * 获取token
     *
     * @return token
     */
    public static String getToken() {
        return get(Constants.TOKEN);
    }

    /**
     * 设置token
     *
     * @param token
     *         token
     */
    public static void setToken(String token) {
        set(Constants.TOKEN, token);
    }

    /**
     * 获取请求方法
     *
     * @return 请求方法
     */
    public static String getRequestMethod() {
        return get(Constants.REQUEST_METHOD);
    }

    /**
     * 设置请求方法
     *
     * @param requestMethod
     *         请求方法
     */
    public static void setRequestMethod(String requestMethod) {
        set(Constants.REQUEST_METHOD, requestMethod);
    }

    /**
     * 获取请求URI
     *
     * @return 请求URI
     */
    public static String getRequestUri() {
        return get(Constants.REQUEST_URI);
    }

    /**
     * 设置请求URI
     *
     * @param requestUri
     *         请求URI
     */
    public static void setRequestUri(String requestUri) {
        set(Constants.REQUEST_URI, requestUri);
    }

    /**
     * 获取完整请求URI
     *
     * @return 完整请求URI
     */
    public static String getCompleteRequestUri() {
        String uri = get(Constants.COMPLETE_REQUEST_URI);
        return uri == null ? getRequestUri() : uri;
    }

    /**
     * 设置完整请求URI
     *
     * @param requestUri
     *         请求URI
     */
    public static void setCompleteRequestUri(String requestUri) {
        set(Constants.COMPLETE_REQUEST_URI, requestUri);
    }

}
