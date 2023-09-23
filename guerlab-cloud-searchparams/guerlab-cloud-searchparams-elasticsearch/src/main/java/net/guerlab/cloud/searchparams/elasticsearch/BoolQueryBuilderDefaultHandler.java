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

package net.guerlab.cloud.searchparams.elasticsearch;

import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.json.JsonData;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsHandler;

/**
 * BoolQuery.Builder默认处理.
 *
 * @author guer
 */
public class BoolQueryBuilderDefaultHandler implements SearchParamsHandler {

	private static TimeZone defaultTimeZone = TimeZone.getDefault();

	public static void setDefaultTimeZone(@Nullable TimeZone val) {
		if (val == null) {
			defaultTimeZone = TimeZone.getDefault();
		}
		else {
			defaultTimeZone = val;
		}
	}

	public static TimeZone getTimeZone() {
		return defaultTimeZone;
	}

	@Override
	public void setValue(Object object, String fieldName, String columnName, Object value, SearchModelType searchModelType,
			@Nullable String customSql, @Nullable JsonField jsonField) {
		BoolQuery.Builder builder = (BoolQuery.Builder) object;

		if (searchModelType == SearchModelType.IS_NULL) {
			builder.mustNot(m -> m.exists(t -> t.field(columnName)));
		}
		else if (searchModelType == SearchModelType.IS_NOT_NULL) {
			builder.must(m -> m.exists(t -> t.field(columnName)));
		}
		else if (searchModelType == SearchModelType.EQUAL_TO) {
			builder.must(m -> m.term(t -> t.field(columnName).value(String.valueOf(value))));
		}
		else if (searchModelType == SearchModelType.NOT_EQUAL_TO) {
			builder.mustNot(m -> m.term(t -> t.field(columnName).value(String.valueOf(value))));
		}
		else if (searchModelType == SearchModelType.LIKE) {
			builder.must(m -> m.queryString(q -> q.fields(columnName).query("^.*(" + value + ").*$")));
		}
		else if (searchModelType == SearchModelType.NOT_LIKE) {
			builder.mustNot(m -> m.queryString(q -> q.fields(columnName).query(".*(" + value + ").*")));
		}
		else if (searchModelType == SearchModelType.START_WITH) {
			builder.must(m -> m.queryString(q -> q.fields(columnName).query("(" + value + ").*")));
		}
		else if (searchModelType == SearchModelType.START_NOT_WITH) {
			builder.mustNot(m -> m.queryString(q -> q.fields(columnName).query("(" + value + ").*")));
		}
		else if (searchModelType == SearchModelType.END_WITH) {
			builder.must(m -> m.queryString(q -> q.fields(columnName).query(".*(" + value + ")")));
		}
		else if (searchModelType == SearchModelType.END_NOT_WITH) {
			builder.mustNot(m -> m.queryString(q -> q.fields(columnName).query(".*(" + value + ")")));
		}
		else if (searchModelType == SearchModelType.GREATER_THAN ||
				searchModelType == SearchModelType.GREATER_THAN_OR_EQUAL_TO ||
				searchModelType == SearchModelType.LESS_THAN ||
				searchModelType == SearchModelType.LESS_THAN_OR_EQUAL_TO) {
			builder.must(m -> m.range(r -> {
				r.field(columnName);
				if (value instanceof Date | value instanceof Temporal) {
					r.timeZone(getTimeZone().getID());
				}

				if (searchModelType == SearchModelType.GREATER_THAN) {
					r.gt(JsonData.of(value));
				}
				if (searchModelType == SearchModelType.GREATER_THAN_OR_EQUAL_TO) {
					r.gte(JsonData.of(value));
				}
				else if (searchModelType == SearchModelType.LESS_THAN) {
					r.lt(JsonData.of(value));
				}
				else if (searchModelType == SearchModelType.LESS_THAN_OR_EQUAL_TO) {
					r.lte(JsonData.of(value));
				}
				return r;
			}));
		}
		else if (searchModelType == SearchModelType.IN) {
			if (value instanceof Collection<?> collection) {
				builder.must(m -> m.queryString(q -> q.fields(columnName)
						.query("^.*(" + collection.stream().filter(Objects::nonNull).map(Object::toString)
								.collect(Collectors.joining("|")) + ").*$")));
			}
			else if (value.getClass().isArray()) {
				Object[] array = (Object[]) value;
				builder.must(m -> m.queryString(q -> q.fields(columnName)
						.query("^.*(" + Arrays.stream(array).filter(Objects::nonNull).map(Object::toString)
								.collect(Collectors.joining("|")) + ").*$")));
			}
			else {
				builder.must(m -> m.queryString(q -> q.fields(columnName).query("^.*(" + value + ").*$")));
			}
		}
		else if (searchModelType == SearchModelType.NOT_IN) {
			if (value instanceof Collection<?> collection) {
				builder.mustNot(m -> m.queryString(q -> q.fields(columnName)
						.query("^.*(" + collection.stream().filter(Objects::nonNull).map(Object::toString)
								.collect(Collectors.joining("|")) + ").*$")));
			}
			else if (value.getClass().isArray()) {
				Object[] array = (Object[]) value;
				builder.mustNot(m -> m.queryString(q -> q.fields(columnName)
						.query("^.*(" + Arrays.stream(array).filter(Objects::nonNull).map(Object::toString)
								.collect(Collectors.joining("|")) + ").*$")));
			}
			else {
				builder.mustNot(m -> m.queryString(q -> q.fields(columnName).query("^.*(" + value + ").*$")));
			}
		}
	}
}
