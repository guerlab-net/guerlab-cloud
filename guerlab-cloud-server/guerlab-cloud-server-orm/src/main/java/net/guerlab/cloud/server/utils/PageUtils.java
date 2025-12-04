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

package net.guerlab.cloud.server.utils;

import java.util.List;
import java.util.ServiceLoader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.orm.QueryWrapperGetter;

/**
 * 分页工具类.
 *
 * @author guer
 */
public final class PageUtils {

	private static final List<SelectPageBeforeHandler> BEFORE_HANDLERS;

	static {
		BEFORE_HANDLERS = ServiceLoader.load(SelectPageBeforeHandler.class).stream().map(ServiceLoader.Provider::get)
				.toList();
	}

	private PageUtils() {

	}

	/**
	 * 查询分页列表.
	 *
	 * @param wrapperGetter QueryWrapper获取接口对象
	 * @param searchParams  搜索参数对象
	 * @param pageId        分页ID
	 * @param pageSize      分页尺寸
	 * @param mapper        mapper对象
	 * @param <E>           实体类型
	 * @param <Q>           搜索参数对象类型
	 * @return 分页结果列表
	 */
	public static <E extends IBaseEntity, Q extends SearchParams> Pageable<E> selectPage(QueryWrapperGetter<E, Q> wrapperGetter,
			Q searchParams, int pageId, int pageSize, BaseMapper<E> mapper) {
		pageId = Math.max(pageId, 1);
		pageSize = pageSize <= 0 ? 10 : pageSize;

		QueryWrapper<E> queryWrapper = wrapperGetter.getQueryWrapperWithSelectMethod(searchParams);
		Page<E> page = new Page<>(pageId, pageSize);

		for (SelectPageBeforeHandler beforeHandler : BEFORE_HANDLERS) {
			beforeHandler.beforeQuery(queryWrapper, searchParams, page);
		}

		Page<E> result = mapper.selectPage(page, queryWrapper);
		List<E> list = result.getRecords();

		long total = result.getTotal();

		Pageable<E> listObject = new Pageable<>(pageSize, total, list);

		listObject.setCurrentPageId(pageId);
		listObject.setMaxPageId((long) Math.ceil((double) total / pageSize));

		return listObject;
	}
}
