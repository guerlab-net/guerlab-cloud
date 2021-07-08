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
package net.guerlab.cloud.server.service;

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

}
