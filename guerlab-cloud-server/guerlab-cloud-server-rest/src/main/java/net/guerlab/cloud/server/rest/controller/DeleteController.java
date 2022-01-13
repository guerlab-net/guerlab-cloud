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
import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * 基础删除控制器接口
 *
 * @param <D>
 *         DTO类型
 * @param <E>
 *         实体类型
 * @param <S>
 *         Service类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface DeleteController<D, E, S extends BaseService<E, PK, SP>, SP extends SearchParams, PK extends Serializable>
        extends IController<D, E, S, PK> {

    /**
     * 请求路径
     */
    String DELETE_BY_ID_PATH = "/{id}";

    /**
     * 路径参数
     */
    String DELETE_BY_ID_PATH_PARAM = "id";

    /**
     * 根据主键ID删除对象
     *
     * @param id
     *         主键ID
     * @param force
     *         强制删除标志
     */
    @Log("method.delete")
    @Operation(summary = "删除", security = @SecurityRequirement(name = Constants.TOKEN))
    @DeleteMapping(DELETE_BY_ID_PATH)
    default void delete(@Parameter(description = "主键ID", required = true) @PathVariable(DELETE_BY_ID_PATH_PARAM) PK id,
            @Parameter(description = "强制删除标志") @RequestParam(required = false) Boolean force) {
        E entity = findOne0(id);
        beforeDelete(entity);
        getService().deleteById(id, force);
        afterDelete(entity);
    }

    /**
     * 删除前置
     *
     * @param entity
     *         实体
     */
    default void beforeDelete(E entity) {

    }

    /**
     * 删除后置
     *
     * @param entity
     *         实体
     */
    default void afterDelete(E entity) {

    }

}
