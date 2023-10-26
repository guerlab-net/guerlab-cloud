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

package net.guerlab.cloud.web.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.api.QueryApi;
import net.guerlab.cloud.commons.api.SelectById;
import net.guerlab.cloud.commons.api.SelectCount;
import net.guerlab.cloud.commons.api.SelectList;
import net.guerlab.cloud.commons.api.SelectOne;
import net.guerlab.cloud.commons.api.SelectPage;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基础查询控制器实现.
 *
 * @param <E>  实体类型
 * @param <SP> 搜索参数类型
 * @param <A>  api接口类型
 * @param <V>  返回对象类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
@Getter
public abstract class BaseQueryController<E, SP extends SearchParams, A extends QueryApi<E, SP>, V> {

	/**
	 * api.
	 */
	protected final A api;

	/**
	 * 根据api实例创建控制器.
	 *
	 * @param api api实例
	 */
	public BaseQueryController(A api) {
		this.api = api;
	}

	/**
	 * 实体对象转换为输出对象.
	 *
	 * @param entity 实体对象
	 * @return 输出对象
	 */
	protected abstract V convert(E entity);

	@Nullable
	@GetMapping(SelectById.SELECT_BY_ID_PATH)
	@Operation(summary = "通过Id查询单一结果", security = @SecurityRequirement(name = Constants.TOKEN))
	public V selectById(@Parameter(description = "ID", required = true) @PathVariable(SelectById.SELECT_BY_ID_PARAM) Long id, @Nullable SP searchParams) {
		E entity = getApi().selectById(id);
		if (entity == null) {
			throw nullPointException();
		}
		V vo = convert(entity);
		afterFind(Collections.singletonList(vo), searchParams);
		return vo;
	}

	@Nullable
	@PostMapping(SelectOne.SELECT_ONE_PATH)
	@Operation(summary = "查询单一结果", security = @SecurityRequirement(name = Constants.TOKEN))
	public V selectOne(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams) {
		beforeFind(searchParams);
		E entity = getApi().selectOne(searchParams);
		if (entity == null) {
			throw nullPointException();
		}
		V vo = convert(entity);
		afterFind(Collections.singletonList(vo), searchParams);
		return vo;
	}

	/**
	 * 当对象为空的时候抛出的异常.
	 *
	 * @return 当对象为空的时候抛出的异常
	 */
	@SuppressWarnings("SameReturnValue")
	protected RuntimeException nullPointException() {
		return new NullPointerException();
	}

	@PostMapping(SelectList.SELECT_LIST_PATH)
	@Operation(summary = "查询列表", security = @SecurityRequirement(name = Constants.TOKEN))
	public List<V> selectList(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams) {
		beforeFind(searchParams);
		List<E> list = getApi().selectList(searchParams);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<V> result = list.stream().map(this::convert).toList();
		afterFind(result, searchParams);
		return result;
	}

	@PostMapping(SelectPage.SELECT_PAGE_PATH)
	@Operation(summary = "查询分页列表", security = @SecurityRequirement(name = Constants.TOKEN))
	public Pageable<V> selectPage(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams,
			@Parameter(description = "分页ID") @RequestParam(name = SelectPage.PAGE_ID, defaultValue = SelectPage.PAGE_ID_VALUE, required = false) int pageId,
			@Parameter(description = "分页尺寸") @RequestParam(name = SelectPage.PAGE_SIZE, defaultValue = SelectPage.PAGE_SIZE_VALUE, required = false) int pageSize) {
		beforeFind(searchParams);
		Pageable<E> result = getApi().selectPage(searchParams, pageId, pageSize);
		if (result.getList().isEmpty()) {
			return Pageable.empty();
		}

		List<V> list = result.getList().stream().map(this::convert).toList();
		afterFind(list, searchParams);

		Pageable<V> voResult = new Pageable<>();
		voResult.setCount(result.getCount());
		voResult.setList(list);
		voResult.setCurrentPageId(result.getCurrentPageId());
		voResult.setMaxPageId(result.getMaxPageId());
		voResult.setPageSize(result.getPageSize());
		return voResult;
	}

	@PostMapping(SelectCount.SELECT_COUNT_PATH)
	@Operation(summary = "查询总记录数", security = @SecurityRequirement(name = Constants.TOKEN))
	public long selectCount(@Parameter(description = "搜索参数对象", required = true) @RequestBody SP searchParams) {
		beforeFind(searchParams);
		return getApi().selectCount(searchParams);
	}

	/**
	 * 查询前置环绕.
	 *
	 * @param searchParams 搜索参数
	 */
	@SuppressWarnings("EmptyMethod")
	protected void beforeFind(SP searchParams) {

	}

	/**
	 * 查询后置焕然.
	 *
	 * @param list         结果列表
	 * @param searchParams 搜索参数
	 */
	@SuppressWarnings("EmptyMethod")
	protected void afterFind(Collection<V> list, @Nullable SP searchParams) {

	}
}
