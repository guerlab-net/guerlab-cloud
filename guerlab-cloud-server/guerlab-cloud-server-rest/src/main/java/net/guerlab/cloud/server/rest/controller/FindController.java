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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.util.BeanConvertUtils;
import net.guerlab.cloud.core.dto.Convert;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import net.guerlab.cloud.server.service.BaseFindService;
import net.guerlab.web.result.ListObject;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.ArrayList;
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
public interface FindController<D, E extends Convert<D>, S extends BaseFindService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        extends IController<E, S, PK> {

    /**
     * 根据主键ID查询对象
     *
     * @param id
     *         主键ID
     * @param searchParams
     *         搜索参数
     * @return 对象
     */
    @Operation(summary = "查询详情", security = @SecurityRequirement(name = Constants.TOKEN))
    @GetMapping("/{id}")
    default D findOne(@Parameter(description = "id", required = true) @PathVariable PK id, SP searchParams) {
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
    @Nullable
    @Operation(summary = "查询单个", security = @SecurityRequirement(name = Constants.TOKEN))
    @GetMapping("/one")
    default D queryOne(SP searchParams) {
        searchParams.setPageId(1);
        searchParams.setPageSize(1);

        Collection<D> list = findList(searchParams).getList();
        if (list.isEmpty()) {
            return null;
        }
        return new ArrayList<>(list).get(0);
    }

    /**
     * 根据搜索参数查询对象列表
     *
     * @param searchParams
     *         搜索参数
     * @return 对象列表
     */
    @Operation(summary = "查询列表", security = @SecurityRequirement(name = Constants.TOKEN))
    @GetMapping
    default ListObject<D> findList(SP searchParams) {
        beforeFind(searchParams);
        ListObject<D> result = BeanConvertUtils.toListObject(getService().selectPage(searchParams));
        afterFind(result.getList(), searchParams);
        return result;
    }

    /**
     * 根据搜索参数查询对象列表
     *
     * @param searchParams
     *         搜索参数
     * @return 对象列表
     */
    @Operation(summary = "查询全部", security = @SecurityRequirement(name = Constants.TOKEN))
    @GetMapping("/all")
    default List<D> findAll(SP searchParams) {
        beforeFind(searchParams);
        List<D> list = BeanConvertUtils.toList(getService().selectAll(searchParams));
        afterFind(list, searchParams);
        return list;
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
    default void afterFind(Collection<D> list, SP searchParams) {

    }

}
