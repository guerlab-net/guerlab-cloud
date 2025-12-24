/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.core.Constants;

/**
 * APi定义.
 *
 * @param <E> 返回实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SelectByIds<E extends IBaseEntity> {

	/**
	 * 请求路径.
	 */
	String SELECT_BY_IDS_PATH = "/search/byIds";

	/**
	 * 根据主键ID列表查询对象列表.
	 *
	 * @param ids 主键ID列表
	 * @return 对象列表
	 */
	@PostMapping(SELECT_BY_IDS_PATH)
	@Operation(summary = "根据主键ID列表查询对象列表", security = @SecurityRequirement(name = Constants.TOKEN))
	List<E> selectByIds(@RequestBody List<Long> ids);
}
