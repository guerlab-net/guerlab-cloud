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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * APi定义.
 *
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface Delete<Q extends SearchParams> {

	/**
	 * 根据搜索参数删除数据.
	 *
	 * @param searchParams 搜索参数
	 */
	@DeleteMapping
	@Operation(summary = "根据搜索参数删除数据", security = @SecurityRequirement(name = Constants.TOKEN))
	void delete(@Parameter(description = "搜索参数", required = true) @RequestBody Q searchParams);
}
