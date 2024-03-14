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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.IBaseEntity;

/**
 * APi定义.
 *
 * @param <E> 返回实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SelectById<E extends IBaseEntity> {

	/**
	 * 请求路径.
	 */
	String SELECT_BY_ID_PATH = "/{id:\\d+}";

	/**
	 * 路径参数名.
	 */
	String SELECT_BY_ID_PARAM = "id";

	/**
	 * 根据主键ID查询对象.
	 *
	 * @param id 主键ID
	 * @return 对象
	 */
	@Nullable
	@GetMapping(SELECT_BY_ID_PATH)
	@Operation(summary = "通过Id查询单一结果", security = @SecurityRequirement(name = Constants.TOKEN))
	E selectById(@Parameter(description = "ID", required = true) @PathVariable(SELECT_BY_ID_PARAM) Long id);

	/**
	 * 通过Id查询单一结果.
	 *
	 * @param id 主键id
	 * @return Optional
	 */
	default Optional<E> selectByIdOptional(Long id) {
		return Optional.ofNullable(selectById(id));
	}
}
