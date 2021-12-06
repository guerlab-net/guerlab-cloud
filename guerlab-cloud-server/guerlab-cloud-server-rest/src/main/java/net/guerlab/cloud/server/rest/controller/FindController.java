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
import net.guerlab.cloud.core.dto.Convert;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseFindService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
 * @param <PK>
 *         主键类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface FindController<D, E extends Convert<D>, S extends BaseFindService<E, PK, SP>, SP extends SearchParams, PK extends Serializable>
        extends IController<E, S, PK>, Api<D, PK, SP> {

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
    default D selectById(@Parameter(description = "id", required = true) @PathVariable PK id,
            @Nullable SP searchParams) {
        D result = findOne0(id).convert();
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
        D result = entity.convert();
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
        List<D> list = BeanConvertUtils.toList(getService().selectList(searchParams));
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
        Pageable<D> result = BeanConvertUtils.toPageable(getService().selectPage(searchParams, pageId, pageSize));
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
