package net.guerlab.smart.platform.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchParamsUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * Example获取接口
 *
 * @param <T>
 *         实体类型
 * @author guer
 */
public interface ExampleGetter<T> {

    /**
     * 获取Example，并通过searchParams对象对Example进行赋值
     *
     * @param searchParams
     *         搜索对象
     * @return Example
     */
    default Example getExampleWithSelectMethod(AbstractSearchParams searchParams) {
        return getExample(searchParams);
    }

    /**
     * 获取Example
     *
     * @return Example
     */
    default Example getExampleWithSelectMethod() {
        return getExample();
    }

    /**
     * 获取Example，并通过searchParams对象对Example进行赋值
     *
     * @param searchParams
     *         搜索对象
     * @return Example
     */
    default Example getExample(AbstractSearchParams searchParams) {
        Example example = getExample();

        SearchParamsUtils.handler(searchParams, example);

        return example;
    }

    /**
     * 获取Example
     *
     * @return Example
     */
    default Example getExample() {
        Class<T> clazz = getEntityClass();

        return new Example(clazz);
    }

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    Class<T> getEntityClass();
}
