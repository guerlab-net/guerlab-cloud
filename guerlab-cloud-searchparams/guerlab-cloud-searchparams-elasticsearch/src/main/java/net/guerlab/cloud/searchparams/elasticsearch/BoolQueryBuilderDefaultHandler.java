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

package net.guerlab.cloud.searchparams.elasticsearch;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Function;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import jakarta.annotation.Nullable;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsHandler;

/**
 * BoolQuery.Builder默认处理.
 *
 * @author guer
 */
public class BoolQueryBuilderDefaultHandler implements SearchParamsHandler {

	private static final List<SearchModelType> notTypes = Arrays.asList(
			SearchModelType.IS_NULL,
			SearchModelType.NOT_EQUAL_TO,
			SearchModelType.NOT_LIKE,
			SearchModelType.START_NOT_WITH,
			SearchModelType.END_NOT_WITH,
			SearchModelType.NOT_IN
	);

	private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	private static final String DATE_FORMATTER = "yyyy-MM-dd";

	private static TimeZone defaultTimeZone = TimeZone.getDefault();

	/**
	 * 设置默认时区.
	 *
	 * @param val 默认时区
	 */
	public static void setDefaultTimeZone(@Nullable TimeZone val) {
		if (val == null) {
			defaultTimeZone = TimeZone.getDefault();
		}
		else {
			defaultTimeZone = val;
		}
	}

	/**
	 * 获取时区信息.
	 *
	 * @return 时区信息
	 */
	public static TimeZone getTimeZone() {
		return defaultTimeZone;
	}

	@Override
	public void setValue(Object object, Field field, String columnName, Object value, SearchModelType searchModelType,
			@Nullable String customSql, @Nullable JsonField jsonField) {
		if (value instanceof OrderBys) {
			return;
		}

		BoolQuery.Builder builder = (BoolQuery.Builder) object;

		boolean isNested = columnName.contains(".");
		List<String> paths = new ArrayList<>();
		if (isNested) {
			String[] pathArray = columnName.split("\\.");
			paths.addAll(Arrays.stream(Arrays.copyOfRange(pathArray, 0, pathArray.length - 1)).toList());
		}

		String queryColumnName = ColumnUtils.getQueryColumnName(field, columnName);

		Function<Query.Builder, ObjectBuilder<Query>> fn;
		if (searchModelType == SearchModelType.IS_NULL || searchModelType == SearchModelType.IS_NOT_NULL) {
			fn = m -> m.exists(t -> t.field(ColumnUtils.getColumnName(field, columnName)));
		}
		else if (searchModelType == SearchModelType.LIKE || searchModelType == SearchModelType.NOT_LIKE) {
			fn = m -> m.wildcard(q -> q.field(queryColumnName).value("*" + value + "*"));
		}
		else if (searchModelType == SearchModelType.START_WITH || searchModelType == SearchModelType.START_NOT_WITH) {
			fn = m -> m.wildcard(q -> q.field(queryColumnName).value(value + "*"));
		}
		else if (searchModelType == SearchModelType.END_WITH || searchModelType == SearchModelType.END_NOT_WITH) {
			fn = m -> m.wildcard(q -> q.field(queryColumnName).value("*" + value));
		}
		else if (searchModelType == SearchModelType.GREATER_THAN ||
				searchModelType == SearchModelType.GREATER_THAN_OR_EQUAL_TO ||
				searchModelType == SearchModelType.LESS_THAN ||
				searchModelType == SearchModelType.LESS_THAN_OR_EQUAL_TO) {
			fn = m -> m.range(r -> {
				r.field(queryColumnName);
				JsonData jsonData = JsonData.of(value);

				if (value instanceof Date || value instanceof Temporal) {
					r.timeZone(getTimeZone().getID());
					if (value instanceof Date date) {
						r.format(DATE_TIME_FORMATTER);
						jsonData = JsonData.of(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
								.format(date.toInstant()));
					}
					else if (value instanceof LocalDateTime dateTime) {
						r.format(DATE_TIME_FORMATTER);
						jsonData = JsonData.of(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER).format(dateTime));
					}
					else if (value instanceof LocalDate date) {
						r.format(DATE_FORMATTER);
						jsonData = JsonData.of(DateTimeFormatter.ofPattern(DATE_FORMATTER).format(date));
					}
				}

				if (searchModelType == SearchModelType.GREATER_THAN) {
					r.gt(jsonData);
				}
				if (searchModelType == SearchModelType.GREATER_THAN_OR_EQUAL_TO) {
					r.gte(jsonData);
				}
				else if (searchModelType == SearchModelType.LESS_THAN) {
					r.lt(jsonData);
				}
				else if (searchModelType == SearchModelType.LESS_THAN_OR_EQUAL_TO) {
					r.lte(jsonData);
				}
				return r;
			});
		}
		else if (searchModelType == SearchModelType.IN || searchModelType == SearchModelType.NOT_IN) {
			List<FieldValue> fieldValues = new ArrayList<>();
			if (value instanceof Iterable<?> collection) {
				for (Object o : collection) {
					fieldValues.add(FieldValue.of(o.toString()));
				}
			}
			else if (value.getClass().isArray()) {
				Object[] array = (Object[]) value;
				for (Object o : array) {
					fieldValues.add(FieldValue.of(o.toString()));
				}
			}

			if (!fieldValues.isEmpty()) {
				fn = m -> m.terms(q -> q.field(queryColumnName).terms(b -> b.value(fieldValues)));
			}
			else {
				fn = m -> m.match(q -> q.field(queryColumnName).query(value.toString()));
			}
		}
		else {
			if (value instanceof String str) {
				fn = m -> m.term(t -> t.field(queryColumnName).value(str));
			}
			else if (value instanceof Long val) {
				fn = m -> m.term(t -> t.field(queryColumnName).value(val));
			}
			else if (value instanceof Boolean val) {
				fn = m -> m.term(t -> t.field(queryColumnName).value(val));
			}
			else if (value instanceof Double val) {
				fn = m -> m.term(t -> t.field(queryColumnName).value(val));
			}
			else {
				FieldValue fieldValue = FieldValue.of(JsonData.of(value));
				fn = m -> m.term(t -> t.field(queryColumnName).value(fieldValue));
			}
		}

		if (!paths.isEmpty()) {
			List<NestedQuery.Builder> nestedQueryBuilders = new ArrayList<>(paths.size());

			for (int i = 0; i < paths.size(); i++) {
				String allPath = String.join(".", paths.subList(0, i + 1));
				NestedQuery.Builder nestedQueryBuilder = new NestedQuery.Builder();
				nestedQueryBuilder.path(allPath);
				nestedQueryBuilder.scoreMode(ChildScoreMode.Max);
				nestedQueryBuilders.add(nestedQueryBuilder);
			}

			int size = nestedQueryBuilders.size() - 1;

			NestedQuery.Builder firstBuilder = nestedQueryBuilders.get(0);
			NestedQuery.Builder lastBuilder = nestedQueryBuilders.get(size);
			lastBuilder.query(fn);

			for (int i = 0; i <= size; i++) {
				NestedQuery.Builder currentBuilder = nestedQueryBuilders.get(i);
				NestedQuery.Builder childBuilder = i < size ? nestedQueryBuilders.get(i + 1) : null;

				if (childBuilder != null) {
					currentBuilder.query(q -> q.nested(n -> childBuilder));
				}
			}

			if (notTypes.contains(searchModelType)) {
				builder.mustNot(q -> q.nested(n -> firstBuilder));
			}
			else {
				builder.must(q -> q.nested(n -> firstBuilder));
			}
		}
		else {
			if (notTypes.contains(searchModelType)) {
				builder.mustNot(fn);
			}
			else {
				builder.must(fn);
			}
		}
	}
}
