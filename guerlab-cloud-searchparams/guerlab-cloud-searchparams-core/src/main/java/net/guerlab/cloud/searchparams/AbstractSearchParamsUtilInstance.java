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

package net.guerlab.cloud.searchparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.Nullable;

/**
 * 抽象搜索参数工具实例.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public abstract class AbstractSearchParamsUtilInstance {

	/**
	 * 处理器列表.
	 */
	private final List<SearchParamsHandlerWrapper> handlers = new ArrayList<>();

	/**
	 * 默认处理器.
	 */
	private SearchParamsHandler defaultHandler;

	/**
	 * 判断是否允许.
	 *
	 * @param object 输出对象
	 * @return 返回true表示进行处理，否则不进行处理
	 */
	public abstract boolean accept(Object object);

	/**
	 * 设置默认处理器对象.
	 *
	 * @param handler 处理器
	 */
	public void setDefaultHandler(SearchParamsHandler handler) {
		defaultHandler = handler;
	}

	/**
	 * 添加处理器对象.
	 *
	 * @param type    数据类型
	 * @param handler 处理器
	 */
	public void addHandler(Class<?> type, SearchParamsHandler handler) {
		SearchParamsHandlerWrapper wrapper = new SearchParamsHandlerWrapper();
		wrapper.handler = handler;
		wrapper.type = type;

		handlers.add(wrapper);
	}

	/**
	 * 移除处理器对象.
	 *
	 * @param type    数据类型
	 * @param handler 处理器
	 */
	public void removeHandler(Class<?> type, SearchParamsHandler handler) {
		SearchParamsHandlerWrapper wrapper = new SearchParamsHandlerWrapper();
		wrapper.handler = handler;
		wrapper.type = type;

		handlers.remove(wrapper);
	}

	/**
	 * 获取处理器对象.
	 *
	 * @param type 数据类型
	 * @return 处理器对象
	 */
	@Nullable
	public SearchParamsHandler getHandler(Class<?> type) {
		Optional<SearchParamsHandlerWrapper> optional = handlers.stream()
				.filter(wrapper -> Objects.equals(type, wrapper.type)).findFirst();

		if (optional.isPresent()) {
			return optional.get().handler;
		}

		optional = handlers.stream().filter(wrapper -> wrapper.type.isAssignableFrom(type)).findFirst();

		if (optional.isPresent()) {
			return optional.get().handler;
		}
		return defaultHandler;
	}

	/**
	 * 后置处理.
	 *
	 * @param searchParams 搜索参数
	 * @param object       输出对象
	 */
	@SuppressWarnings("EmptyMethod")
	public void afterHandler(SearchParams searchParams, Object object) {

	}

	/**
	 * 搜索参数处理器包装.
	 */
	private static class SearchParamsHandlerWrapper {

		/**
		 * 类型.
		 */
		private Class<?> type;

		/**
		 * 处理器.
		 */
		private SearchParamsHandler handler;

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			SearchParamsHandlerWrapper wrapper = (SearchParamsHandlerWrapper) o;
			return Objects.equals(type, wrapper.type) && Objects.equals(handler, wrapper.handler);
		}

		@Override
		public int hashCode() {
			return Objects.hash(type, handler);
		}
	}

}
