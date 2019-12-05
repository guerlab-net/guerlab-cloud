package net.guerlab.smart.platform.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.web.result.ListObject;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Optional;

/**
 * 基本查询服务接口
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
public interface BaseFindService<T, PK> extends ExampleGetter<T> {

    /**
     * 查询单一结果，根据实体内非null字段按照值相等方式查询
     *
     * @param entity
     *         实体
     * @return 实体
     */
    T selectOne(T entity);

    /**
     * 查询单一结果，根据实体内非null字段按照值相等方式查询
     *
     * @param entity
     *         实体
     * @return Optional
     */
    default Optional<T> selectOneOptional(T entity) {
        return Optional.ofNullable(selectOne(entity));
    }

    /**
     * 查询单一结果，根据搜索参数进行筛选
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体
     */
    T selectOne(AbstractSearchParams searchParams);

    /**
     * 查询单一结果，根据搜索参数进行筛选
     *
     * @param searchParams
     *         搜索参数对象
     * @return Optional
     */
    default Optional<T> selectOneOptional(AbstractSearchParams searchParams) {
        return Optional.ofNullable(selectOne(searchParams));
    }

    /**
     * 通过Id查询单一结果
     *
     * @param id
     *         主键id
     * @return 实体
     */
    T selectById(PK id);

    /**
     * 通过Id查询单一结果
     *
     * @param id
     *         主键id
     * @return Optional
     */
    default Optional<T> selectByIdOptional(PK id) {
        return Optional.ofNullable(selectById(id));
    }

    /**
     * 查询列表
     *
     * @param entity
     *         实体
     * @return 实体列表
     */
    Collection<T> selectList(T entity);

    /**
     * 查询列表
     *
     * @param example
     *         Example查询对象
     * @return 实体列表
     */
    Collection<T> selectList(Example example);

    /**
     * 获取所有对象
     *
     * @return 实体列表
     */
    Collection<T> selectAll();

    /**
     * 获取所有对象
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体列表
     */
    Collection<T> selectAll(AbstractSearchParams searchParams);

    /**
     * 查询列表
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体列表
     */
    ListObject<T> selectPage(AbstractSearchParams searchParams);

    /**
     * 查询总记录数
     *
     * @param entity
     *         实体
     * @return 实体总数
     */
    int selectCount(T entity);

    /**
     * 查询总记录数
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体总数
     */
    int selectCount(AbstractSearchParams searchParams);

}
