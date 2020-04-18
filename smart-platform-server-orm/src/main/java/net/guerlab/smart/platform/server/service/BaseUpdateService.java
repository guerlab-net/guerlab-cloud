package net.guerlab.smart.platform.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;
import tk.mybatis.mapper.entity.Example;

/**
 * 基本更新服务接口
 *
 * @param <T>
 *         数据类型
 * @author guer
 */
@SuppressWarnings("UnusedReturnValue")
public interface BaseUpdateService<T> extends ExampleGetter<T> {

    /**
     * 根据id更新
     *
     * @param entity
     *         实体
     * @return 是否更新成功
     */
    boolean updateById(T entity);

    /**
     * 不update null
     *
     * @param entity
     *         实体
     * @return 是否更新成功
     */
    boolean updateSelectiveById(T entity);

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param example
     *         条件
     * @return 是否更新成功
     */
    boolean updateByExample(T entity, Example example);

    /**
     * 根据条件更新 不update null
     *
     * @param entity
     *         实体
     * @param example
     *         条件
     * @return 是否更新成功
     */
    boolean updateByExampleSelective(T entity, Example example);

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param searchParams
     *         搜索条件
     * @return 是否更新成功
     */
    boolean updateBySearchParams(T entity, AbstractSearchParams searchParams);

    /**
     * 根据条件更新 不update null
     *
     * @param entity
     *         实体
     * @param searchParams
     *         搜索条件
     * @return 是否更新成功
     */
    boolean updateBySearchParamsSelective(T entity, AbstractSearchParams searchParams);

}
