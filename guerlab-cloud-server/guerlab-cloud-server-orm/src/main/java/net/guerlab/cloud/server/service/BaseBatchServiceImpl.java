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

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
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
public abstract class BaseBatchServiceImpl<T, PK extends Serializable, M extends BatchMapper<T>, SP extends AbstractSearchParams>
        extends BaseServiceImpl<T, PK, M, SP> implements BaseBatchSaveService<T, SP> {

    protected static final int DEFAULT_BATCH_SIZE = 1000;

    private final Log log = LogFactory.getLog(this.getClass());

    protected <E> boolean executeBatch(Collection<E> list, BiConsumer<SqlSession, E> consumer) {
        return executeBatch(list, DEFAULT_BATCH_SIZE, consumer);
    }

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
