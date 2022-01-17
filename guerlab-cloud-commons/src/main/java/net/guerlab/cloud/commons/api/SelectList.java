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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

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
public interface SelectList<D, SP extends SearchParams> {

    /**
     * 请求路径
     */
    String SELECT_LIST_PATH = "/search/list";

    /**
     * 查询列表
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体列表
     */
    @PostMapping(SELECT_LIST_PATH)
    @Operation(summary = "查询列表", security = @SecurityRequirement(name = Constants.TOKEN))
    Collection<D> selectList(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams);
}
