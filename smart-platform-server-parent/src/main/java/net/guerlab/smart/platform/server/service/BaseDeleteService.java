package net.guerlab.smart.platform.server.service;

/**
 * 基本删除服务接口
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
public interface BaseDeleteService<T, PK> extends ExampleGetter<T> {

    /**
     * 删除
     *
     * @param entity
     *         实体
     */
    default void delete(T entity) {
        delete(entity, false);
    }

    /**
     * 根据Id删除
     *
     * @param id
     *         主键值
     */
    default void deleteById(PK id) {
        deleteById(id, false);
    }

    /**
     * 删除
     *
     * @param entity
     *         实体
     * @param force
     *         强制删除标志
     */
    void delete(T entity, Boolean force);

    /**
     * 根据Id删除
     *
     * @param id
     *         主键值
     * @param force
     *         强制删除标志
     */
    void deleteById(PK id, Boolean force);

}
