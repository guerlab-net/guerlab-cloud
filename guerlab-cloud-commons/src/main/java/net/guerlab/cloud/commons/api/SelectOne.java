/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * APi定义.
 *
 * @param <E>  返回实体类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SelectOne<E extends IBaseEntity, SP extends SearchParams> {

	/**
	 * 请求路径.
	 */
	String SELECT_ONE_PATH = "/search/one";

	/**
	 * 查询单一结果，根据搜索参数进行筛选.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 实体
	 */
	@Nullable
	@PostMapping(SELECT_ONE_PATH)
	@Operation(summary = "查询单一结果", security = @SecurityRequirement(name = Constants.TOKEN))
	E selectOne(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams);

	/**
	 * 查询单一结果，根据搜索参数进行筛选.
	 *
	 * @param searchParams 搜索参数对象
	 * @return Optional
	 */
	default Optional<E> selectOneOptional(SP searchParams) {
		return Optional.ofNullable(selectOne(searchParams));
	}
}
