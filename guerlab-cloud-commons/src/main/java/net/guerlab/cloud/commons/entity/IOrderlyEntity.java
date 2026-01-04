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

package net.guerlab.cloud.commons.entity;

import java.util.Comparator;

import jakarta.annotation.Nullable;

import net.guerlab.cloud.core.Constants;

/**
 * 可排序对象接口.
 *
 * @param <E> 对象类型
 * @author guer
 */
public interface IOrderlyEntity<E extends IOrderlyEntity<E>> extends Comparable<E> {

	/**
	 * 排序方法.
	 *
	 * @return 排序方法
	 */
	static Comparator<IOrderlyEntity<?>> comparator() {
		return Comparator.comparing(IOrderlyEntity::orderNum, Comparator.nullsFirst(Comparator.naturalOrder()));
	}

	/**
	 * 根据排序值返回排序顺序.
	 *
	 * @param o1 参与排序对象1
	 * @param o2 参与排序对象2
	 * @return 小于0时，在参与排序对象之前。
	 * 大于0时，在参与排序对象之后。
	 * 等于0时，顺序保持不变
	 */
	static int compareTo(IOrderlyEntity<?> o1, IOrderlyEntity<?> o2) {
		return comparator().compare(o1, o2);
	}

	/**
	 * 获取排序值.
	 *
	 * @return 排序值
	 */
	@Nullable
	Long orderNum();

	/**
	 * 设置排序值.
	 *
	 * @param orderNum 排序值
	 */
	void orderNum(@Nullable Long orderNum);

	/**
	 * 根据排序值返回排序顺序.
	 *
	 * @param o 参与排序对象
	 * @return 小于0时，在参与排序对象之前。
	 * 大于0时，在参与排序对象之后。
	 * 等于0时，顺序保持不变
	 */
	@Override
	default int compareTo(E o) {
		return compareTo(this, o);
	}

	/**
	 * 排序值默认处理.
	 */
	default void orderNumDefaultHandler() {
		if (orderNum() == null) {
			orderNum(Constants.DEFAULT_ORDER_NUM);
		}
	}
}
