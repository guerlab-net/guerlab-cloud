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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * APi定义
 *
 * @author guer
 */
@SuppressWarnings("unused")
public interface DeleteById {

    /**
     * 请求路径
     */
    String DELETE_BY_ID_PATH = "/{id}";

    /**
     * 路径参数名
     */
    String DELETE_BY_ID_PARAM = "id";

    /**
     * 根据Id删除数据
     *
     * @param id
     *         主键ID
     */
    @DeleteMapping(DELETE_BY_ID_PATH)
    @Operation(summary = "根据Id删除数据", security = @SecurityRequirement(name = Constants.TOKEN))
    void deleteById(@Parameter(description = "ID", required = true) @PathVariable(DELETE_BY_ID_PARAM) Long id);
}
