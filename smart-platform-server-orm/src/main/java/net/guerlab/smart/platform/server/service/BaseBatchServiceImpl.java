package net.guerlab.smart.platform.server.service;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.smart.platform.server.mappers.BatchMapper;
import net.guerlab.smart.platform.server.utils.BatchSaveUtils;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
@Transactional(rollbackFor = Exception.class)
public abstract class BaseBatchServiceImpl<T, PK extends Serializable, M extends BatchMapper<T>, SP extends AbstractSearchParams>
        extends BaseServiceImpl<T, PK, M, SP> implements BaseBatchSaveService<T, SP> {

    @Override
    public Collection<T> batchInsert(Collection<T> collection) {
        List<T> list = BatchSaveUtils.filter(collection, this::batchSaveBefore);

        if (CollectionUtil.isNotEmpty(list)) {
            saveBatch(list);
        }

        return list;
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
    protected abstract T batchSaveBefore(T entity);
}
