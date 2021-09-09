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
package net.guerlab.cloud.api.headers;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 请求头上下文处理器
 *
 * @author guer
 */
@SuppressWarnings("unused")
public abstract class HeadersContextHandler {

    private final static InheritableThreadLocal<Map<String, String>> THREAD_LOCAL = new InheritableThreadLocal<>() {

        @Override
        protected Map<String, String> initialValue() {
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
    public static void set(String key, String value) {
        THREAD_LOCAL.get().put(key, value);
    }

    /**
     * 获取内容
     *
     * @param key
     *         key
     * @return 内容
     */
    @SuppressWarnings({ "SameParameterValue" })
    @Nullable
    public static String get(String key) {
        return THREAD_LOCAL.get().get(key);
    }

    /**
     * 对内容遍历
     *
     * @param action
     *         遍历处理
     */
    public static void forEach(BiConsumer<? super String, ? super String> action) {
        THREAD_LOCAL.get().forEach(action);
    }

    /**
     * 清除当前内容
     */
    public static void clean() {
        THREAD_LOCAL.remove();
    }

}
