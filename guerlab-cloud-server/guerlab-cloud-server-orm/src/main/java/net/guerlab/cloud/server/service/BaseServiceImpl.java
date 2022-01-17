/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.core.sequence.Sequence;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.utils.BatchSaveUtils;
import net.guerlab.cloud.server.utils.PageUtils;
import net.guerlab.commons.collection.CollectionUtil;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 基本服务实现
 *
 * @param <T>
 *         数据类型
 * @param <M>
 *         Mapper类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings({ "EmptyMethod", "unused" })
@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T, M extends BaseMapper<T>, SP extends SearchParams>
        implements BaseService<T, SP> {

    /**
     * 默认单次操作数量
     */
    protected static final int DEFAULT_BATCH_SIZE = 1000;

    private static final Log LOGGER = LogFactory.getLog(BaseServiceImpl.class);

    /**
     * 实体类型
     */
    protected final Class<T> entityClass = this.currentModelClass();

    /**
     * mapper类型
     */
    protected final Class<T> mapperClass = this.currentMapperClass();

    /**
     * 序列
     */
    protected Sequence sequence;

    /**
     * mapper
     */
    protected M baseMapper;

    /**
     * 获取mapper
     *
     * @return mapper
     */
    public final M getBaseMapper() {
        return this.baseMapper;
    }

    /**
     * 设置mapper对象
     *
     * @param baseMapper
     *         mapper对象
     */
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setBaseMapper(M baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public T selectOne(T entity) {
        QueryWrapper<T> queryWrapper = getQueryWrapper();
        queryWrapper.setEntity(entity);
        return getBaseMapper().selectOne(queryWrapper);
    }

    @Override
    public T selectOne(SP searchParams) {
        return getBaseMapper().selectOne(getQueryWrapperWithSelectMethod(searchParams));
    }

    @Override
    public T selectById(Long id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public Collection<T> selectList(T entity) {
        QueryWrapper<T> queryWrapper = getQueryWrapper();
        queryWrapper.setEntity(entity);
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public Collection<T> selectList(QueryWrapper<T> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public Collection<T> selectList(LambdaQueryWrapper<T> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public Collection<T> selectList() {
        return selectList(getQueryWrapper());
    }

    @Override
    public Collection<T> selectList(SP searchParams) {
        return selectList(getQueryWrapperWithSelectMethod(searchParams));
    }

    @Override
    public Pageable<T> selectPage(SP searchParams, int pageId, int pageSize) {
        return PageUtils.selectPage(this, searchParams, pageId, pageSize, getBaseMapper());
    }

    @Override
    public int selectCount(T entity) {
        QueryWrapper<T> queryWrapper = getQueryWrapper();
        queryWrapper.setEntity(entity);
        return getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public int selectCount(SP searchParams) {
        return getBaseMapper().selectCount(getQueryWrapperWithSelectMethod(searchParams));
    }

    @Override
    public void insert(T entity) {
        insertBefore(entity);
        getBaseMapper().insert(entity);
        insertAfter(entity);
    }

    /**
     * 添加前
     *
     * @param entity
     *         实体
     */
    protected void insertBefore(T entity) {
        /* 默认空实现 */
    }

    /**
     * 添加后
     *
     * @param entity
     *         实体
     */
    protected void insertAfter(T entity) {
        /* 默认空实现 */
    }

    @Override
    public Collection<T> batchInsert(Collection<T> collection) {
        List<T> list = BatchSaveUtils.filter(collection, this::batchSaveBefore);

        if (CollectionUtil.isNotEmpty(list)) {
            saveBatch(list, DEFAULT_BATCH_SIZE);
        }

        return list;
    }

    /**
     * 保存检查
     *
     * @param entity
     *         实体
     * @return 如果返回null则不保存该对象，否则保存该对象
     */
    @Nullable
    protected T batchSaveBefore(T entity) {
        try {
            insertBefore(entity);
            return entity;
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * 批量保存
     *
     * @param entityList
     *         实体列表
     * @param batchSize
     *         单次操作数量
     */
    @SuppressWarnings("SameParameterValue")
    protected final void saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = SqlHelper.getSqlStatement(this.mapperClass, SqlMethod.INSERT_ONE);
        this.executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    /**
     * 批量执行
     *
     * @param list
     *         实体列表
     * @param batchSize
     *         单次执行数量
     * @param consumer
     *         操作内容
     * @param <E>
     *         实体类型
     */
    protected <E> void executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        SqlHelper.executeBatch(this.entityClass, LOGGER, list, batchSize, consumer);
    }

    @Override
    public boolean updateById(T entity) {
        updateBefore(entity);
        int result = getBaseMapper().updateById(entity);
        updateAfter(entity);
        return result > 0;
    }

    @Override
    public boolean update(T entity, LambdaQueryWrapper<T> queryWrapper) {
        return getBaseMapper().update(entity, queryWrapper) > 0;
    }

    @Override
    public boolean update(T entity, QueryWrapper<T> queryWrapper) {
        return getBaseMapper().update(entity, queryWrapper) > 0;
    }

    @Override
    public boolean update(T entity, SP searchParams) {
        return getBaseMapper().update(entity, getQueryWrapper(searchParams)) > 0;
    }

    /**
     * 更新前
     *
     * @param entity
     *         实体
     */
    protected void updateBefore(T entity) {
        /* 默认空实现 */
    }

    /**
     * 更新后
     *
     * @param entity
     *         实体
     */
    protected void updateAfter(T entity) {
        /* 默认空实现 */
    }

    @Override
    public void delete(SP searchParams) {
        deleteBefore(searchParams);
        QueryWrapper<T> queryWrapper = getQueryWrapper(searchParams);
        getBaseMapper().delete(queryWrapper);
        deleteAfter(searchParams);
    }

    /**
     * 删除前
     *
     * @param searchParams
     *         搜索参数
     */
    protected void deleteBefore(SP searchParams) {
        /* 默认空实现 */
    }

    /**
     * 删除后
     *
     * @param searchParams
     *         搜索参数
     */
    protected void deleteAfter(SP searchParams) {
        /* 默认空实现 */
    }

    @Override
    public void deleteById(Long id) {
        deleteByIdBefore(id);
        getBaseMapper().deleteById(id);
        deleteByIdAfter(id);
    }

    /**
     * 删除前
     *
     * @param id
     *         id
     */
    protected void deleteByIdBefore(Long id) {
        /* 默认空实现 */
    }

    /**
     * 删除后
     *
     * @param id
     *         id
     */
    protected void deleteByIdAfter(Long id) {
        /* 默认空实现 */
    }

    @Override
    public void delete(LambdaQueryWrapper<T> queryWrapper) {
        getBaseMapper().delete(queryWrapper);
    }

    @Override
    public void delete(QueryWrapper<T> queryWrapper) {
        getBaseMapper().delete(queryWrapper);
    }

    /**
     * 获取当前mapper对象类型
     *
     * @return mapper对象类型
     */
    @SuppressWarnings("unchecked")
    protected Class<T> currentMapperClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    /**
     * 获取当前模型对象类型
     *
     * @return 模型对象类型
     */
    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), 0);
    }

    /**
     * 设置序列
     *
     * @param sequence
     *         序列
     */
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
}
