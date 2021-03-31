package net.guerlab.smart.platform.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象上下文处理器
 *
 * @author guer
 */
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
    @SuppressWarnings("unchecked")
    protected static <T> T get(String key) {
        return (T) THREAD_LOCAL.get().get(key);
    }

    /**
     * 清除当前内容
     */
    public static void clean() {
        THREAD_LOCAL.remove();
    }
}
