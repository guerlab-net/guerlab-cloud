package net.guerlab.smart.platform.server.service;

import java.util.Collection;

/**
 * 基本批量保存服务接口
 *
 * @param <T>
 *         数据类型
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public interface BaseBatchSaveService<T> extends ExampleGetter<T> {

    /**
     * 批量保存
     *
     * @param collection
     *         待保存列表
     * @return 已保存列表
     */
    Collection<T> batchInsert(Collection<T> collection);

    /**
     * 批量保存
     *
     * @param collection
     *         待保存列表
     * @return 已保存列表
     */
    Collection<T> replaceBatchInsert(Collection<T> collection);

}
