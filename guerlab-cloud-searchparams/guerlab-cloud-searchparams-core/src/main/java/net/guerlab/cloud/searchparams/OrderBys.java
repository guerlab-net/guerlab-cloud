/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * 排序字段列表.
 *
 * @author guer
 */
public class OrderBys extends ArrayList<OrderBy> {

	/**
	 * 创建排序字段列表.
	 */
	public OrderBys() {
	}

	/**
	 * 根据排序列表创建排序字段列表.
	 *
	 * @param c 排序列表
	 */
	public OrderBys(Collection<OrderBy> c) {
		super(c);
	}

	/**
	 * 根据排序列表创建排序字段列表.
	 *
	 * @param orderBys 排序列表
	 * @return 排序字段列表
	 */
	public static OrderBys of(OrderBy... orderBys) {
		return new OrderBys(Arrays.stream(orderBys).filter(Objects::nonNull).toList());
	}
}
