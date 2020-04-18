package net.guerlab.smart.platform.server.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import net.guerlab.spring.commons.sequence.Sequence;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

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
 * @author guer
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T, PK, M extends Mapper<T>> implements BaseService<T, PK> {

    /**
     * 序列
     */
    protected Sequence sequence;

    /**
     * mapper
     */
    protected M mapper;

    @Autowired
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public T selectOne(AbstractSearchParams searchParams) {
        Example example = getExample(searchParams);
        return mapper.selectOneByExample(example);
    }

    @Override
    public T selectById(PK id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Collection<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public Collection<T> selectList(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public Collection<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public Collection<T> selectAll(AbstractSearchParams searchParams) {
        Example example = getExample(searchParams);

        return mapper.selectByExample(example);
    }

    @Override
    public ListObject<T> selectPage(AbstractSearchParams searchParams) {
        Example example = getExample(searchParams);

        int pageId = Math.max(searchParams.getPageId(), 1);
        int pageSize = searchParams.getPageSize();

        Page<T> result = PageMethod.startPage(pageId, pageSize);
        Collection<T> list = mapper.selectByExample(example);

        long total = result.getTotal();

        ListObject<T> listObject = new ListObject<>(searchParams.getPageSize(), total, list);

        listObject.setCurrentPageId(pageId);
        listObject.setMaxPageId((long) Math.ceil((double) total / pageSize));

        return listObject;
    }

    @Override
    public int selectCount(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public int selectCount(AbstractSearchParams searchParams) {
        Example example = getExample(searchParams);

        return mapper.selectCountByExample(example);
    }

    @Override
    public void insert(T entity) {
        insertBefore(entity);
        mapper.insert(entity);
        insertAfter(entity);
    }

    @Override
    public void insertSelective(T entity) {
        insertBefore(entity);
        mapper.insertSelective(entity);
        insertAfter(entity);
    }

    /**
     * 添加前
     *
     * @param entity
     *         实体
     */
    protected void insertBefore(T entity) {

    }

    /**
     * 添加后
     *
     * @param entity
     *         实体
     */
    protected void insertAfter(T entity) {

    }

    @Override
    public boolean updateById(T entity) {
        updateBefore(entity);
        int result = mapper.updateByPrimaryKey(entity);
        updateAfter(entity);
        return result > 0;
    }

    @Override
    public boolean updateSelectiveById(T entity) {
        updateBefore(entity);
        int result = mapper.updateByPrimaryKeySelective(entity);
        updateAfter(entity);
        return result > 0;
    }

    @Override
    public boolean updateByExample(T entity, Example example) {
        return mapper.updateByExample(entity, example) > 0;
    }

    @Override
    public boolean updateByExampleSelective(T entity, Example example) {
        return mapper.updateByExampleSelective(entity, example) > 0;
    }

    @Override
    public boolean updateBySearchParams(T entity, AbstractSearchParams searchParams) {
        return mapper.updateByExample(entity, getExample(searchParams)) > 0;
    }

    @Override
    public boolean updateBySearchParamsSelective(T entity, AbstractSearchParams searchParams) {
        return mapper.updateByExampleSelective(entity, getExample(searchParams)) > 0;
    }

    /**
     * 更新前
     *
     * @param entity
     *         实体
     */
    protected void updateBefore(T entity) {

    }

    /**
     * 更新后
     *
     * @param entity
     *         实体
     */
    protected void updateAfter(T entity) {

    }

    @Override
    public void delete(T entity, Boolean force) {
        deleteBefore(entity, force);
        mapper.delete(entity);
        deleteAfter(entity, force);
    }

    /**
     * 删除前
     *
     * @param entity
     *         实体
     * @param force
     *         强制删除标签
     */
    protected void deleteBefore(T entity, Boolean force) {

    }

    /**
     * 删除后
     *
     * @param entity
     *         实体
     * @param force
     *         强制删除标签
     */
    protected void deleteAfter(T entity, Boolean force) {

    }

    @Override
    public void deleteById(PK id, Boolean force) {
        deleteByIdBefore(id, force);
        mapper.deleteByPrimaryKey(id);
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

    }

}
