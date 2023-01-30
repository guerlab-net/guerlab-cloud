/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.core.result;

import java.util.Collection;
import java.util.Collections;

import lombok.Data;

import org.springframework.lang.Nullable;

/**
 * 可分页对象.
 *
 * @param <T> 数据类型
 * @author guer
 */
@Data
@SuppressWarnings("unused")
public class Pageable<T> {

	/**
	 * 空可分页对象.
	 */
	@SuppressWarnings("rawtypes")
	public static final Pageable EMPTY = new EmptyPageable<>();

	/**
	 * 数据列表.
	 */
	private Collection<T> list = Collections.emptyList();

	/**
	 * 数据总数.
	 */
	private long count = 0L;

	/**
	 * 查询内容数量.
	 */
	private long pageSize = 10L;

	/**
	 * 当前页面ID.
	 */
	private long currentPageId = 1L;

	/**
	 * 最大页面ID.
	 */
	private long maxPageId = 1L;

	/**
	 * 无参构造.
	 */
	public Pageable() {
		/*
		 * not to do something
		 */
	}

	/**
	 * 通过分页尺寸构造对象.
	 *
	 * @param pageSize 分页尺寸
	 */
	public Pageable(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 通过分页尺寸、数据总数构造对象.
	 *
	 * @param pageSize 分页尺寸
	 * @param count    数据总数
	 */
	public Pageable(long pageSize, long count) {
		this.pageSize = pageSize;
		this.count = count;
	}

	/**
	 * 通过分页尺寸、数据总数、数据列表构造对象.
	 *
	 * @param pageSize 分页尺寸
	 * @param count    数据总数
	 * @param dataList 数据列表
	 */
	public Pageable(long pageSize, long count, @Nullable Collection<T> dataList) {
		this(pageSize, count);

		if (dataList != null) {
			list = dataList;
		}
	}

	/**
	 * 获取空列表对象.
	 *
	 * @param <T> 参数对象类型
	 * @return 空列表对象
	 */
	@SuppressWarnings({"unchecked", "SameReturnValue"})
	public static <T> Pageable<T> empty() {
		return EMPTY;
	}

	private static class EmptyPageable<E> extends Pageable<E> {

		@Override
		public void setList(Collection<E> list) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setCount(long count) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setPageSize(long pageSize) {
			throw new UnsupportedOperationException();
		}
	}
}
