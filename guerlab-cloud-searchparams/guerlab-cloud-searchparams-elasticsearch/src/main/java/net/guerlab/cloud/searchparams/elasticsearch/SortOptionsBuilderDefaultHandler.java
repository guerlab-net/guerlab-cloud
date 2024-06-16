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

package net.guerlab.cloud.searchparams.elasticsearch;

import java.util.List;

import co.elastic.clients.elasticsearch._types.SortOrder;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.OrderBy;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsHandler;

/**
 * BoolQuery.Builder默认处理.
 *
 * @author guer
 */
public class SortOptionsBuilderDefaultHandler implements SearchParamsHandler {

	@Override
	public void setValue(Object object, String fieldName, String columnName, Object value, SearchModelType searchModelType,
			@Nullable String customSql, @Nullable JsonField jsonField) {
		if (!(value instanceof OrderBys)) {
			return;
		}

		NativeQueryBuilder builder = (NativeQueryBuilder) object;

		List<OrderBy> orderBys = (OrderBys) value;
		orderBys = orderBys.stream().filter(this::orderByFilter).toList();

		for (OrderBy orderBy : orderBys) {
			builder.withSort(s -> s.field(b -> b.field(orderBy.getColumnName())
					.order(orderBy.isAsc() ? SortOrder.Asc : SortOrder.Desc)));
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
