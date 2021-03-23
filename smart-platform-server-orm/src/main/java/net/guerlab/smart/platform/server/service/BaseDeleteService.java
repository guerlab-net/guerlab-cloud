package net.guerlab.smart.platform.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;

import java.io.Serializable;

/**
 * 基本删除服务接口
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
public interface BaseDeleteService<T, PK extends Serializable, SP extends AbstractSearchParams>
        extends QueryWrapperGetter<T, SP> {

    /**
     * 删除
     *
     * @param searchParams
     *         搜索参数
     */
    default void delete(SP searchParams) {
        delete(searchParams, false);
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
     * @param searchParams
     *         搜索参数
     * @param force
     *         强制删除标志
     */
    void delete(SP searchParams, Boolean force);

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
