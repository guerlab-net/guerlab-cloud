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

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.mappers.BatchMapper;
import net.guerlab.cloud.server.utils.BatchSaveUtils;
import net.guerlab.commons.collection.CollectionUtil;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 基本服务实现
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @param <M>
 *         Mapper类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
@Transactional(rollbackFor = Exception.class)
public abstract class BaseBatchServiceImpl<T, PK extends Serializable, M extends BatchMapper<T>, SP extends SearchParams>
        extends BaseServiceImpl<T, PK, M, SP> implements BaseBatchSaveService<T, SP> {

    /**
     * 默认单次操作数量
     */
    protected static final int DEFAULT_BATCH_SIZE = 1000;

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * 批量执行
     *
     * @param list
     *         实体列表
     * @param consumer
     *         操作内容
     * @param <E>
     *         实体类型
     * @return 执行结果
     */
    protected <E> boolean executeBatch(Collection<E> list, BiConsumer<SqlSession, E> consumer) {
        return executeBatch(list, DEFAULT_BATCH_SIZE, consumer);
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
     * @return 执行结果
     */
    protected <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.entityClass, this.log, list, batchSize, consumer);
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

    @Override
    public Collection<T> replaceBatchInsert(Collection<T> collection) {
        List<T> list = BatchSaveUtils.filter(collection, this::batchSaveBefore);

        if (CollectionUtil.isNotEmpty(list)) {
            getBaseMapper().replaceInsertList(list);
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
    protected abstract T batchSaveBefore(T entity);
}
