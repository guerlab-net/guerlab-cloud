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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 基础更新控制器接口
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
public interface UpdateController<D, E, S extends BaseService<E, SP>, SP extends SearchParams>
        extends IController<D, E, S>, ModifyControllerWrapper<D, E> {

    /**
     * 请求路径
     */
    String UPDATE_BY_ID_PATH = "/{id}";

    /**
     * 路径参数
     */
    String UPDATE_BY_ID_PATH_PARAM = "id";

    /**
     * 根据主键ID编辑对象
     *
     * @param id
     *         主键ID
     * @param dto
     *         dto对象
     * @return 编辑后的dto对象
     */
    @Log("method.update")
    @Operation(summary = "编辑", security = @SecurityRequirement(name = Constants.TOKEN))
    @PostMapping(UPDATE_BY_ID_PATH)
    default D update(@Parameter(description = "主键ID", required = true) @PathVariable(UPDATE_BY_ID_PATH_PARAM) Long id,
            @Parameter(description = "对象数据", required = true) @RequestBody D dto) {
        E entity = findOne0(id);
        beforeUpdate(entity, dto);
        copyProperties(dto, entity, id);
        getService().updateById(entity);
        afterUpdate(entity, dto);
        return convert(entity);
    }

    /**
     * 编辑前置
     *
     * @param entity
     *         实体
     * @param dto
     *         dto对象
     */
    default void beforeUpdate(E entity, D dto) {

    }

    /**
     * 编辑后置
     *
     * @param entity
     *         实体
     * @param dto
     *         dto对象
     */
    default void afterUpdate(E entity, D dto) {

    }

}
