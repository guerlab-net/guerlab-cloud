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

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nullable;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * 集合元素处理.
 */
public class CollectionHandler extends AbstractMyBatisPlusSearchParamsHandler {

	private static String buildReplacement(int size) {
		StringBuilder replacementBuilder = new StringBuilder();
		replacementBuilder.append("(");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				replacementBuilder.append(", ");
			}
			replacementBuilder.append("{");
			replacementBuilder.append(i);
			replacementBuilder.append("}");
		}
		replacementBuilder.append(")");
		return replacementBuilder.toString();
	}

	@Override
	public Class<?> acceptClass() {
		return Collection.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object object, String fieldName, String columnName, Object value,
			SearchModelType searchModelType, @Nullable String customSql, @Nullable JsonField jsonField) {
		Collection<Object> collection = (Collection<Object>) value;

		if (collection.isEmpty()) {
			return;
		}

		List<Object> list = collection.stream().filter(Objects::nonNull).toList();

		if (list.isEmpty()) {
			return;
		}

		QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
		columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());

		if (jsonField != null) {
			DbType dbType = DbTypeUtils.getDbType(object);
			String jsonPath = getJsonPath(jsonField);
			String sqlTemplate = dbType.formatJsonQuerySql(columnName, searchModelType, jsonPath, list.size());
			if (sqlTemplate == null) {
				return;
			}

			Object[] params = list.stream().map(item -> dbType.jsonQueryValueFormat(item, searchModelType, jsonPath))
					.toArray();

			wrapper.and(w -> w.apply(sqlTemplate, params));
		}
		else {
			if (searchModelType == SearchModelType.CUSTOM_SQL) {
				if (customSql == null) {
					return;
				}

				CustomerSqlInfo info = new CustomerSqlInfo(customSql);
				String sql = info.sql;
				if (info.matchFlag) {
					if (info.batch) {
						while (sql.contains(CustomerSqlInfo.BATCH_FLAG)) {
							sql = sql.replaceFirst(CustomerSqlInfo.BATCH_REG, buildReplacement(list.size()));
						}
					}

					sql = sql.replaceAll(CustomerSqlInfo.MATCH_REG, "{0}");

					if (info.batch) {
						wrapper.apply(sql, list.toArray());
					}
					else {
						wrapper.apply(sql, list.get(0));
					}
				}
				else {
					wrapper.apply(sql);
				}
			}
			else if (searchModelType == SearchModelType.NOT_IN) {
				List<List<Object>> splitList = CollectionHelper.split(list);
				if (splitList.size() > 1) {
					for (List<Object> subList : splitList) {
						wrapper.notIn(columnName, subList);
					}
				}
				else {
					wrapper.notIn(columnName, list);
				}
			}
			else {
				List<List<Object>> splitList = CollectionHelper.split(list);
				if (splitList.size() > 1) {
					String finalColumnName = columnName;
					wrapper.and(w -> {
						for (List<Object> subList : splitList) {
							w.or().in(finalColumnName, subList);
						}
					});
				}
				else {
					wrapper.in(columnName, list);
				}
			}
		}
	}
}
