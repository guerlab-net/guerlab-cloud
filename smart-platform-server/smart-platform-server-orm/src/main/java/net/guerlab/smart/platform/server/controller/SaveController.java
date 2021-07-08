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
package net.guerlab.smart.platform.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * 基础保存控制器接口
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
public interface SaveController<D, E extends Convert<D>, S extends BaseService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        extends IController<E, S, PK>, ModifyControllerWrapper<D, E, PK> {

    /**
     * 添加对象
     *
     * @param dto
     *         dto对象
     * @return 添加的dto对象
     */
    @Operation(summary = "添加", security = @SecurityRequirement(name = Constants.TOKEN))
    @PostMapping
    default D save(@Parameter(description = "对象数据", required = true) @RequestBody D dto) {
        E entity = newEntity();
        beforeSave(dto);
        copyProperties(dto, entity, null);
        getService().insert(entity);
        afterSave(entity, dto);
        return entity.convert();
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
