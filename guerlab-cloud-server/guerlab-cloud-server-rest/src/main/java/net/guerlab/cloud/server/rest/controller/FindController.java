/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
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
import net.guerlab.cloud.server.service.BaseFindService;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.web.result.ListObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
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
     * @return 对象
     */
    @Operation(summary = "查询详情", security = @SecurityRequirement(name = Constants.TOKEN))
    @GetMapping("/{id}")
    default D findOne(@Parameter(description = "id", required = true) @PathVariable PK id) {
        return findOne0(id).convert();
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
        return BeanConvertUtils.toListObject(getService().selectPage(searchParams));
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
        return BeanConvertUtils.toList(getService().selectAll(searchParams));
    }

    /**
     * 查询前置环绕
     *
     * @param searchParams
     *         搜索参数
     */
    default void beforeFind(SP searchParams) {

    }

}
