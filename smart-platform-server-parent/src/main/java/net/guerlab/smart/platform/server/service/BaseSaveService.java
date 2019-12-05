package net.guerlab.smart.platform.server.service;

/**
 * 基本保存服务接口
 *
 * @param <T>
 *         数据类型
 * @author guer
 */
public interface BaseSaveService<T> extends ExampleGetter<T> {

    /**
     * 添加
     *
     * @param entity
     *         实体
     */
    void insert(T entity);

    /**
     * 插入 不插入null字段
     *
     * @param entity
     *         实体
     */
    void insertSelective(T entity);

}
