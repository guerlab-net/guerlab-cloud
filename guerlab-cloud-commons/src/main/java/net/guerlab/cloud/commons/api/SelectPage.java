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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * APi定义.
 *
 * @param <E> 返回实体类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SelectPage<E extends IBaseEntity, Q extends SearchParams> {

	/**
	 * 请求路径.
	 */
	String SELECT_PAGE_PATH = "/search/page";

	/**
	 * 参数名-分页ID.
	 */
	String PAGE_ID = "pageId";
	/**
	 * 参数名-分页尺寸.
	 */
	String PAGE_SIZE = "pageSize";
	/**
	 * 参数默认值-分页ID.
	 */
	String PAGE_ID_VALUE = "1";
	/**
	 * 参数默认值-分页尺寸.
	 */
	String PAGE_SIZE_VALUE = "10";

	/**
	 * 查询分页列表.
	 *
	 * @param searchParams 搜索参数对象
	 * @param pageId       分页ID
	 * @param pageSize     分页尺寸
	 * @return 实体分页列表
	 */
	@PostMapping(SELECT_PAGE_PATH)
	@Operation(summary = "查询分页列表", security = @SecurityRequirement(name = Constants.TOKEN))
	Pageable<E> selectPage(@Parameter(description = "搜索参数对象", required = true) @RequestBody Q searchParams,
			@Parameter(description = "分页ID") @RequestParam(name = PAGE_ID, defaultValue = PAGE_ID_VALUE, required = false) int pageId,
			@Parameter(description = "分页尺寸") @RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_VALUE, required = false) int pageSize);

}
