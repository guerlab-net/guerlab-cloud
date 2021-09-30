package net.guerlab.cloud.auth.redis;

/**
 * redis操作包装对象
 *
 * @param <T>
 *         数据实体类型
 * @author guer
 */
public interface RedisOperationsWrapper<T> {

    /**
     * 保存对象
     *
     * @param key
     *         key
     * @param entity
     *         对象
     * @param timeout
     *         超时时间
     * @return 是否成功
     */
    boolean put(String key, T entity, long timeout);

    /**
     * 获取对象
     *
     * @param key
     *         key
     * @return 对象
     */
    T get(String key);
}
