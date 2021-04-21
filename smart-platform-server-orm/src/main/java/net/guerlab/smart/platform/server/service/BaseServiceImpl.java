package net.guerlab.smart.platform.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.guerlab.spring.commons.sequence.Sequence;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

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
public abstract class BaseServiceImpl<T, PK extends Serializable, M extends BaseMapper<T>, SP extends AbstractSearchParams>
        extends ServiceImpl<M, T> implements BaseService<T, PK, SP> {

    /**
     * 序列
     */
    protected Sequence sequence;

    @Override
    public T selectOne(T entity) {
        QueryWrapper<T> queryWrapper = getQueryWrapper();
        queryWrapper.setEntity(entity);
        return getBaseMapper().selectOne(queryWrapper);
    }

    @Override
    public T selectOne(SP searchParams) {
        QueryWrapper<T> queryWrapper = getQueryWrapperWithSelectMethod(searchParams);
        return getBaseMapper().selectOne(queryWrapper);
    }

    @Override
    public T selectById(PK id) {
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
    public Collection<T> selectAll() {
        return selectList(getQueryWrapper());
    }

    @Override
    public Collection<T> selectAll(SP searchParams) {
        QueryWrapper<T> queryWrapper = getQueryWrapperWithSelectMethod(searchParams);
        return selectList(queryWrapper);
    }

    @Override
    public ListObject<T> selectPage(SP searchParams) {
        int pageId = Math.max(searchParams.getPageId(), 1);
        int pageSize = searchParams.getPageSize();

        QueryWrapper<T> queryWrapper = getQueryWrapperWithSelectMethod(searchParams);

        Page<T> result = getBaseMapper().selectPage(new Page<>(pageId, pageSize), queryWrapper);
        Collection<T> list = result.getRecords();

        long total = result.getTotal();

        ListObject<T> listObject = new ListObject<>(searchParams.getPageSize(), total, list);

        listObject.setCurrentPageId(pageId);
        listObject.setMaxPageId((long) Math.ceil((double) total / pageSize));

        return listObject;
    }

    @Override
    public int selectCount(T entity) {
        QueryWrapper<T> queryWrapper = getQueryWrapper();
        queryWrapper.setEntity(entity);
        return getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public int selectCount(SP searchParams) {
        QueryWrapper<T> queryWrapper = getQueryWrapperWithSelectMethod(searchParams);

        return getBaseMapper().selectCount(queryWrapper);
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
    public void delete(SP searchParams, Boolean force) {
        deleteBefore(searchParams, force);
        QueryWrapper<T> queryWrapper = getQueryWrapper(searchParams);
        getBaseMapper().delete(queryWrapper);
        deleteAfter(searchParams, force);
    }

    /**
     * 删除前
     *
     * @param searchParams
     *         搜索参数
     * @param force
     *         强制删除标签
     */
    protected void deleteBefore(SP searchParams, Boolean force) {
        /* 默认空实现 */
    }

    /**
     * 删除后
     *
     * @param searchParams
     *         搜索参数
     * @param force
     *         强制删除标签
     */
    protected void deleteAfter(SP searchParams, Boolean force) {
        /* 默认空实现 */
    }

    @Override
    public void deleteById(PK id, Boolean force) {
        deleteByIdBefore(id, force);
        getBaseMapper().deleteById(id);
        deleteByIdAfter(id, force);
    }

    /**
     * 删除前
     *
     * @param id
     *         id
     * @param force
     *         强制删除标签
     */
    protected void deleteByIdBefore(PK id, Boolean force) {
        /* 默认空实现 */
    }

    /**
     * 删除后
     *
     * @param id
     *         id
     * @param force
     *         强制删除标签
     */
    protected void deleteByIdAfter(PK id, Boolean force) {
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

    @Autowired
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
}
