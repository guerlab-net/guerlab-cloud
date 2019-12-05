package net.guerlab.smart.platform.basic.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象上下文处理器
 *
 * @author guer
 */
public abstract class AbstractContextHandler {

    private final static ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

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
