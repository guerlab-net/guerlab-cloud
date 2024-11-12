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

package net.guerlab.cloud.searchparams.mybatisplus;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.OrderBy;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * 排序类型处理.
 *
 * @author guer
 */
public class OrderBysHandler extends AbstractMyBatisPlusSearchParamsHandler {

	@Override
	public Class<?> acceptClass() {
		return OrderBys.class;
	}

	@Override
	public void setValue(Object object, String fieldName, String columnName, Object value,
			SearchModelType searchModelType, @Nullable String customSql, @Nullable JsonField jsonField) {
		QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
		List<OrderBy> orderBys = (OrderBys) value;
		orderBys = orderBys.stream().filter(this::orderByFilter).toList();

		Class<?> entityClass = wrapper.getEntityClass();

		for (OrderBy orderBy : orderBys) {
			wrapper.orderBy(true, orderBy.isAsc(),
					ColumnNameGetter.getColumnName(orderBy.getColumnName(), entityClass));
		}
	}

	/**
	 * 排序字段过滤.
	 *
	 * @param orderBy 排序字段
	 * @return 排序字段是否可以
	 */
	private boolean orderByFilter(@Nullable OrderBy orderBy) {
		if (orderBy == null) {
			return false;
		}

		String columnName = StringUtils.trimToNull(orderBy.getColumnName());
		if (columnName == null) {
			return false;
		}

		orderBy.setColumnName(columnName);
		return true;
	}
}
