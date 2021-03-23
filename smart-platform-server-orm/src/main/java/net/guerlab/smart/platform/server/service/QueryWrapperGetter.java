package net.guerlab.smart.platform.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchParamsUtils;

/**
 * QueryWrapper获取接口
 *
 * @param <T>
 *         实体类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
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
     * 获取实体类型
     *
     * @return 实体类型
     */
    Class<T> getEntityClass();
}
