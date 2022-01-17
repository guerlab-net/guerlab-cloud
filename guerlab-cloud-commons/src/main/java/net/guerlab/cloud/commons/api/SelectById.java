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

package net.guerlab.cloud.commons.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.searchparams.SearchParams;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * APi定义
 *
 * @param <D>
 *         返回实体类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SelectById<D, SP extends SearchParams> {

    /**
     * 请求路径
     */
    String SELECT_BY_ID_PATH = "/{id}";

    /**
     * 路径参数名
     */
    String SELECT_BY_ID_PARAM = "id";

    /**
     * 根据主键ID查询对象
     *
     * @param id
     *         主键ID
     * @param searchParams
     *         搜索参数
     * @return 对象
     */
    @Nullable
    @GetMapping(SELECT_BY_ID_PATH)
    @Operation(summary = "通过Id查询单一结果", security = @SecurityRequirement(name = Constants.TOKEN))
    D selectById(@Parameter(description = "ID", required = true) @PathVariable(SELECT_BY_ID_PARAM) Long id,
            @Nullable SP searchParams);

    /**
     * 通过Id查询单一结果
     *
     * @param id
     *         主键id
     * @return 实体
     */
    @Nullable
    default D selectById(Long id) {
        return selectById(id, null);
    }

    /**
     * 通过Id查询单一结果
     *
     * @param id
     *         主键id
     * @return Optional
     */
    default Optional<D> selectByIdOptional(Long id) {
        return Optional.ofNullable(selectById(id, null));
    }
}
