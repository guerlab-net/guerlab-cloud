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

import java.util.Collection;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;

import static net.guerlab.cloud.commons.api.DeleteById.DELETE_BY_ID_PARAM;
import static net.guerlab.cloud.commons.api.DeleteById.DELETE_BY_ID_PATH;
import static net.guerlab.cloud.commons.api.SelectById.SELECT_BY_ID_PARAM;
import static net.guerlab.cloud.commons.api.SelectById.SELECT_BY_ID_PATH;
import static net.guerlab.cloud.commons.api.SelectCount.SELECT_COUNT_PATH;
import static net.guerlab.cloud.commons.api.SelectList.SELECT_LIST_PATH;
import static net.guerlab.cloud.commons.api.SelectOne.SELECT_ONE_PATH;
import static net.guerlab.cloud.commons.api.SelectPage.PAGE_ID;
import static net.guerlab.cloud.commons.api.SelectPage.PAGE_ID_VALUE;
import static net.guerlab.cloud.commons.api.SelectPage.PAGE_SIZE;
import static net.guerlab.cloud.commons.api.SelectPage.PAGE_SIZE_VALUE;
import static net.guerlab.cloud.commons.api.SelectPage.SELECT_PAGE_PATH;
import static net.guerlab.cloud.commons.api.UpdateById.UPDATE_BY_ID_PARAM;
import static net.guerlab.cloud.commons.api.UpdateById.UPDATE_BY_ID_PATH;

/**
 * APi定义.
 *
 * @param <E>
 *         返回实体类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface Api<E, SP extends SearchParams> {

	/**
	 * 根据主键ID查询对象.
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
	E selectById(@Parameter(description = "主键ID", required = true) @PathVariable(SELECT_BY_ID_PARAM) Long id,
			@Nullable SP searchParams);

	/**
	 * 通过Id查询单一结果.
	 *
	 * @param id
	 *         主键id
	 * @return 实体
	 */
	@Nullable
	default E selectById(Long id) {
		return selectById(id, null);
	}

	/**
	 * 通过Id查询单一结果.
	 *
	 * @param id
	 *         主键id
	 * @return Optional
	 */
	default Optional<E> selectByIdOptional(Long id) {
		return Optional.ofNullable(selectById(id, null));
	}

	/**
	 * 查询单一结果，根据搜索参数进行筛选.
	 *
	 * @param searchParams
	 *         搜索参数对象
	 * @return 实体
	 */
	@Nullable
	@PostMapping(SELECT_ONE_PATH)
	@Operation(summary = "查询单一结果", security = @SecurityRequirement(name = Constants.TOKEN))
	E selectOne(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams);

	/**
	 * 查询单一结果，根据搜索参数进行筛选.
	 *
	 * @param searchParams
	 *         搜索参数对象
	 * @return Optional
	 */
	default Optional<E> selectOneOptional(SP searchParams) {
		return Optional.ofNullable(selectOne(searchParams));
	}

	/**
	 * 查询列表.
	 *
	 * @param searchParams
	 *         搜索参数对象
	 * @return 实体列表
	 */
	@PostMapping(SELECT_LIST_PATH)
	@Operation(summary = "查询列表", security = @SecurityRequirement(name = Constants.TOKEN))
	Collection<E> selectList(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams);

	/**
	 * 查询分页列表.
	 *
	 * @param searchParams
	 *         搜索参数对象
	 * @param pageId
	 *         分页ID
	 * @param pageSize
	 *         分页尺寸
	 * @return 实体分页列表
	 */
	@PostMapping(SELECT_PAGE_PATH)
	@Operation(summary = "查询分页列表", security = @SecurityRequirement(name = Constants.TOKEN))
	Pageable<E> selectPage(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams,
			@Parameter(description = "分页ID") @RequestParam(name = PAGE_ID, defaultValue = PAGE_ID_VALUE, required = false) int pageId,
			@Parameter(description = "分页尺寸") @RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_VALUE, required = false) int pageSize);

	/**
	 * 查询总记录数.
	 *
	 * @param searchParams
	 *         搜索参数对象
	 * @return 实体总数
	 */
	@PostMapping(SELECT_COUNT_PATH)
	@Operation(summary = "查询总记录数", security = @SecurityRequirement(name = Constants.TOKEN))
	long selectCount(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams);

	/**
	 * 新增实体.
	 *
	 * @param entity
	 *         实体
	 * @return 保存后的实体
	 */
	@PostMapping
	@Operation(summary = "新增实体", security = @SecurityRequirement(name = Constants.TOKEN))
	E insert(@RequestBody E entity);

	/**
	 * 根据Id编辑数据.
	 *
	 * @param id
	 *         主键ID
	 * @param entity
	 *         实体
	 * @return 更新后的实体
	 */
	@PostMapping(UPDATE_BY_ID_PATH)
	@Operation(summary = "根据Id编辑数据", security = @SecurityRequirement(name = Constants.TOKEN))
	E updateById(@Parameter(description = "ID", required = true) @PathVariable(UPDATE_BY_ID_PARAM) Long id,
			@RequestBody E entity);

	/**
	 * 根据Id删除数据.
	 *
	 * @param id
	 *         主键ID
	 */
	@DeleteMapping(DELETE_BY_ID_PATH)
	@Operation(summary = "根据Id删除数据", security = @SecurityRequirement(name = Constants.TOKEN))
	void deleteById(@Parameter(description = "ID", required = true) @PathVariable(DELETE_BY_ID_PARAM) Long id);

	/**
	 * 根据搜索参数删除数据.
	 *
	 * @param searchParams
	 *         搜索参数
	 */
	@DeleteMapping
	@Operation(summary = "根据搜索参数删除数据", security = @SecurityRequirement(name = Constants.TOKEN))
	void delete(@Parameter(description = "搜索参数", required = true) @RequestBody SP searchParams);
}
