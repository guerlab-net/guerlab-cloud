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
package net.guerlab.cloud.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.server.service.BaseService;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.spring.searchparams.AbstractSearchParams;
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
public interface DeleteController<D, E extends Convert<D>, S extends BaseService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        extends IController<E, S, PK> {

    /**
     * 根据主键ID删除对象
     *
     * @param id
     *         主键ID
     * @param force
     *         强制删除标志
     */
    @Operation(summary = "删除", security = @SecurityRequirement(name = Constants.TOKEN))
    @DeleteMapping("/{id}")
    default void delete(@Parameter(description = "id", required = true) @PathVariable PK id,
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
