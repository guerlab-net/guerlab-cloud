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
package net.guerlab.cloud.server.api.rest.controller;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.commons.api.Api;
import net.guerlab.cloud.commons.util.BeanConvertUtils;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseFindService;
import net.guerlab.commons.collection.CollectionUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.guerlab.cloud.commons.api.SelectById.SELECT_BY_ID_PARAM;
import static net.guerlab.cloud.commons.api.SelectPage.*;

/**
 * 基础控制器
 *
 * @param <D>
 *         返回实体类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class BaseApi<D, PK extends Serializable, SP extends SearchParams, E, S extends BaseFindService<E, PK, SP>>
        implements Api<D, PK, SP> {

    /**
     * 服务接口
     */
    protected final S service;

    public BaseApi(S service) {
        this.service = service;
    }

    /**
     * 获取服务接口
     *
     * @return 服务接口
     */
    public S getService() {
        return service;
    }

    /**
     * 转换对象
     *
     * @param entity
     *         实体对象
     * @return 输出对象
     */
    protected abstract D convert(E entity);

    @Nullable
    @Override
    public D selectById(@PathVariable(value = SELECT_BY_ID_PARAM) PK id, @Nullable SP searchParams) {
        E entity = getService().selectById(id);
        if (entity == null) {
            return null;
        }
        D result = convert(entity);
        afterFind(Collections.singletonList(result), searchParams);
        return result;
    }

    @Nullable
    @Override
    public D selectOne(@RequestBody SP searchParams) {
        beforeFind(searchParams);
        E entity = getService().selectOne(searchParams);
        if (entity == null) {
            return null;
        }
        D result = convert(entity);
        afterFind(Collections.singletonList(result), searchParams);
        return result;
    }

    @Override
    public Collection<D> selectList(@RequestBody SP searchParams) {
        beforeFind(searchParams);
        List<D> list = CollectionUtil.toList(getService().selectList(searchParams), this::convert);
        afterFind(list, searchParams);
        return list;
    }

    @Override
    public Pageable<D> selectPage(@RequestBody SP searchParams,
            @RequestParam(name = PAGE_ID, defaultValue = PAGE_ID_VALUE, required = false) int pageId,
            @RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_VALUE, required = false) int pageSize) {
        beforeFind(searchParams);
        Pageable<D> result = BeanConvertUtils.toPageable(getService().selectPage(searchParams, pageId, pageSize),
                this::convert);
        afterFind(result.getList(), searchParams);
        return result;
    }

    @Override
    public int selectCount(@RequestBody SP searchParams) {
        beforeFind(searchParams);
        return getService().selectCount(searchParams);
    }

    /**
     * 查询前置环绕
     *
     * @param searchParams
     *         搜索参数
     */
    @SuppressWarnings("EmptyMethod")
    protected void beforeFind(SP searchParams) {

    }

    /**
     * 查询后置焕然
     *
     * @param list
     *         结果列表
     * @param searchParams
     *         搜索参数
     */
    @SuppressWarnings("EmptyMethod")
    protected void afterFind(Collection<D> list, @Nullable SP searchParams) {

    }

}
