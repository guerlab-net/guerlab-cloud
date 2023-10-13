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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * APi定义.
 *
 * @param <E> 返回实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface UpdateById<E> {

	/**
	 * 请求路径.
	 */
	String UPDATE_BY_ID_PATH = "/updateById";

	/**
	 * 根据Id编辑数据.
	 *
	 * @param entity 实体
	 * @return 是否更新成功
	 */
	@PostMapping(UPDATE_BY_ID_PATH)
	@Operation(summary = "根据Id编辑数据")
	boolean updateById(@RequestBody E entity);
}
