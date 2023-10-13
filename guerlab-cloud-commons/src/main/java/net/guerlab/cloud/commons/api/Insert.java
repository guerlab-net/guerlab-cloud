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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.guerlab.cloud.commons.Constants;

/**
 * APi定义.
 *
 * @param <E> 返回实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface Insert<E> {

	/**
	 * 新增实体.
	 *
	 * @param entity 实体
	 * @return 保存后的实体
	 */
	@PostMapping
	@Operation(summary = "新增实体", security = @SecurityRequirement(name = Constants.TOKEN))
	E insert(@RequestBody E entity);
}
