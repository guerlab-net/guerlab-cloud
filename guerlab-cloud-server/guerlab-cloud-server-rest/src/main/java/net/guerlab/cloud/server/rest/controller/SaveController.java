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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 基础保存控制器接口
 *
 * @param <D>
 *         DTO类型
 * @param <E>
 *         实体类型
 * @param <S>
 *         Service类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SaveController<D, E, S extends BaseService<E, SP>, SP extends SearchParams>
        extends IController<D, E, S>, ModifyControllerWrapper<D, E> {

    /**
     * 添加对象
     *
     * @param dto
     *         dto对象
     * @return 添加的dto对象
     */
    @Log("method.save")
    @Operation(summary = "添加", security = @SecurityRequirement(name = Constants.TOKEN))
    @PutMapping
    default D save(@Parameter(description = "对象数据", required = true) @RequestBody D dto) {
        E entity = newEntity();
        beforeSave(dto);
        copyProperties(dto, entity, null);
        getService().insert(entity);
        afterSave(entity, dto);
        return convert(entity);
    }

    /**
     * 保存前置
     *
     * @param dto
     *         dto对象
     */
    default void beforeSave(D dto) {

    }

    /**
     * 保存后置
     *
     * @param entity
     *         实体
     * @param dto
     *         dto对象
     */
    default void afterSave(E entity, D dto) {

    }

}
