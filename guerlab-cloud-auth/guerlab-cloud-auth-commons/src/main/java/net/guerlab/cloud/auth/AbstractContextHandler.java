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
import org.springframework.lang.Nullable;

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
    @Nullable
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
    @Nullable
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
}
