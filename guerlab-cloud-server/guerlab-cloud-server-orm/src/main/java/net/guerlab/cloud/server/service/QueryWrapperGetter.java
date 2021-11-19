/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import net.guerlab.cloud.searchparams.SearchParamsUtils;

/**
 * QueryWrapper获取接口
 *
 * @param <T>
 *         实体类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface QueryWrapperGetter<T, SP extends AbstractSearchParams> {

    /**
     * 获取QueryWrapper，并通过searchParams对象对QueryWrapper进行赋值
     *
     * @param searchParams
     *         搜索对象
     * @return QueryWrapper
     */
    default QueryWrapper<T> getQueryWrapperWithSelectMethod(SP searchParams) {
        return getQueryWrapper(searchParams);
    }

    /**
     * 获取QueryWrapper
     *
     * @return QueryWrapper
     */
    default QueryWrapper<T> getQueryWrapperWithSelectMethod() {
        return getQueryWrapper();
    }

    /**
     * 获取QueryWrapper，并通过searchParams对象对QueryWrapper进行赋值
     *
     * @param searchParams
     *         搜索对象
     * @return QueryWrapper
     */
    default QueryWrapper<T> getQueryWrapper(SP searchParams) {
        QueryWrapper<T> wrapper = getQueryWrapper();

        SearchParamsUtils.handler(searchParams, wrapper);

        return wrapper;
    }

    /**
     * 获取QueryWrapper
     *
     * @return QueryWrapper
     */
    default QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 获取LambdaQueryWrapper
     *
     * @return LambdaQueryWrapper
     */
    default LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    Class<T> getEntityClass();
}
