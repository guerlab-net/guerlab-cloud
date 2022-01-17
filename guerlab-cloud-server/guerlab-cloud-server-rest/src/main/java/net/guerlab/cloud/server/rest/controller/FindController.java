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
package net.guerlab.cloud.server.rest.controller;

import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.guerlab.cloud.commons.api.SelectById.SELECT_BY_ID_PARAM;
import static net.guerlab.cloud.commons.api.SelectPage.*;

/**
 * 基础查询控制器接口
 *
 * @param <D>
 *         DTO类型
 * @param <E>
 *         实体类型
 * @param <S>
 *         Service类型
 * @param <SP>
 *         SearchParams类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface FindController<D, E, S extends BaseFindService<E, SP>, SP extends SearchParams>
        extends IController<D, E, S>, Api<D, SP> {

    /**
     * 根据主键ID查询对象
     *
     * @param id
     *         主键ID
     * @param searchParams
     *         搜索参数
     * @return 对象
     */
    @Override
    @Nullable
    default D selectById(@Parameter(description = "主键ID", required = true) @PathVariable(SELECT_BY_ID_PARAM) Long id,
            @Nullable SP searchParams) {
        D result = convert(findOne0(id));
        afterFind(Collections.singletonList(result), searchParams);
        return result;
    }

    /**
     * 根据搜索参数查询对象
     *
     * @param searchParams
     *         搜索参数
     * @return 对象
     */
    @Override
    @Nullable
    default D selectOne(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams) {
        beforeFind(searchParams);
        E entity = getService().selectOne(searchParams);
        if (entity == null) {
            throw nullPointException();
        }
        D result = convert(entity);
        afterFind(Collections.singletonList(result), searchParams);
        return result;
    }

    /**
     * 根据搜索参数查询对象列表
     *
     * @param searchParams
     *         搜索参数
     * @return 对象列表
     */
    @Override
    default List<D> selectList(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams) {
        beforeFind(searchParams);
        List<D> list = CollectionUtil.toList(getService().selectList(searchParams), this::convert);
        afterFind(list, searchParams);
        return list;
    }

    /**
     * 根据搜索参数查询对象列表
     *
     * @param searchParams
     *         搜索参数
     * @param pageId
     *         分页ID
     * @param pageSize
     *         分页尺寸
     * @return 对象列表
     */
    @Override
    default Pageable<D> selectPage(@RequestBody SP searchParams,
            @RequestParam(name = PAGE_ID, defaultValue = PAGE_ID_VALUE, required = false) int pageId,
            @RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_VALUE, required = false) int pageSize) {
        beforeFind(searchParams);
        Pageable<D> result = BeanConvertUtils.toPageable(getService().selectPage(searchParams, pageId, pageSize),
                this::convert);
        afterFind(result.getList(), searchParams);
        return result;
    }

    /**
     * 根据搜索参数查询对象数量
     *
     * @param searchParams
     *         搜索参数
     * @return 对象数量
     */
    @Override
    default int selectCount(@RequestBody SP searchParams) {
        beforeFind(searchParams);
        return getService().selectCount(searchParams);
    }

    /**
     * 查询前置环绕
     *
     * @param searchParams
     *         搜索参数
     */
    default void beforeFind(SP searchParams) {

    }

    /**
     * 查询后置焕然
     *
     * @param list
     *         结果列表
     * @param searchParams
     *         搜索参数
     */
    default void afterFind(Collection<D> list, @Nullable SP searchParams) {

    }

}
