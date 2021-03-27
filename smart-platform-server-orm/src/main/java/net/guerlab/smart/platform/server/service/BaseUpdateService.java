package net.guerlab.smart.platform.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.spring.searchparams.AbstractSearchParams;

/**
 * 基本更新服务接口
 *
 * @param <T>
 *         数据类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings("UnusedReturnValue")
public interface BaseUpdateService<T, SP extends AbstractSearchParams> extends QueryWrapperGetter<T, SP> {

    /**
     * 根据id更新
     *
     * @param entity
     *         实体
     * @return 是否更新成功
     */
    boolean updateById(T entity);

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param queryWrapper
     *         条件
     * @return 是否更新成功
     */
    boolean update(T entity, LambdaQueryWrapper<T> queryWrapper);

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param queryWrapper
     *         条件
     * @return 是否更新成功
     */
    boolean update(T entity, QueryWrapper<T> queryWrapper);

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param searchParams
     *         搜索条件
     * @return 是否更新成功
     */
    boolean update(T entity, SP searchParams);

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param queryWrapper
     *         条件
     * @return 是否更新成功
     */
    @Deprecated
    default boolean updateByQueryWrapper(T entity, QueryWrapper<T> queryWrapper) {
        return update(entity, queryWrapper);
    }

    /**
     * 根据条件更新
     *
     * @param entity
     *         实体
     * @param searchParams
     *         搜索条件
     * @return 是否更新成功
     */
    @Deprecated
    default boolean updateBySearchParams(T entity, SP searchParams) {
        return update(entity, searchParams);
    }

}
