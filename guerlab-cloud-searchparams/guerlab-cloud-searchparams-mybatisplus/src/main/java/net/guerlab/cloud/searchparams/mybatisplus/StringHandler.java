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

package net.guerlab.cloud.searchparams.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * 字符串类型处理.
 *
 * @author guer
 */
public class StringHandler extends AbstractMyBatisPlusSearchParamsHandler {

	@Override
	public Class<?> acceptClass() {
		return String.class;
	}

	@Override
	public void setValue(Object object, String fieldName, String columnName, Object value,
			SearchModelType searchModelType, @Nullable String customSql, @Nullable JsonField jsonField) {
		String str = StringUtils.trimToNull((String) value);

		if (str == null) {
			return;
		}

		QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
		columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());

		if (jsonField != null) {
			setValueWithJsonField(wrapper, columnName, str, searchModelType, jsonField);
		}
		else {
			setValueWithoutJsonField(wrapper, columnName, str, searchModelType, customSql);
		}
	}

	private void setValueWithJsonField(QueryWrapper<?> wrapper, String columnName, String str,
			SearchModelType searchModelType, JsonField jsonField) {
		DbType dbType = DbTypeUtils.getDbType(wrapper);
		String jsonPath = getJsonPath(jsonField);
		if (dbType == DbType.MYSQL) {
			String sqlTemplate;
			if (searchModelType == SearchModelType.NOT_IN) {
				sqlTemplate = "JSON_SEARCH(%s, 'one', {0}, null, '%s') IS NULL";
			}
			else {
				sqlTemplate = "JSON_SEARCH(%s, 'one', {0}, null, '%s') IS NOT NULL";
			}
			wrapper.apply(sqlTemplate.formatted(columnName, jsonPath), str);
		}
		else if (dbType == DbType.ORACLE) {
			String sqlTemplate;
			if (searchModelType == SearchModelType.NOT_IN) {
				sqlTemplate = "json_exists(%s, {0})";
				str = "%s?(!(@ == \"%s\"))".formatted(jsonPath, str);
			}
			else {
				sqlTemplate = "json_exists(%s, {0})";
				str = "%s?(@ == \"%s\")".formatted(jsonPath, str);
			}

			wrapper.apply(sqlTemplate.formatted(columnName), str);
		}
	}

	private void setValueWithoutJsonField(QueryWrapper<?> wrapper, String columnName, String str,
			SearchModelType searchModelType, @Nullable String customSql) {
		switch (searchModelType) {
		case IS_NULL -> {
			if (Boolean.TRUE.toString().equalsIgnoreCase(str)) {
				wrapper.isNull(columnName);
			}
			else if (Boolean.FALSE.toString().equalsIgnoreCase(str)) {
				wrapper.isNotNull(columnName);
			}
			else {
				wrapper.isNull(columnName);
			}
		}
		case IS_NOT_NULL -> {
			if (Boolean.TRUE.toString().equalsIgnoreCase(str)) {
				wrapper.isNotNull(columnName);
			}
			else if (Boolean.FALSE.toString().equalsIgnoreCase(str)) {
				wrapper.isNull(columnName);
			}
			else {
				wrapper.isNotNull(columnName);
			}
		}
		case NOT_EQUAL_TO -> wrapper.ne(columnName, str);
		case GREATER_THAN -> wrapper.gt(columnName, str);
		case GREATER_THAN_OR_EQUAL_TO -> wrapper.ge(columnName, str);
		case LESS_THAN -> wrapper.lt(columnName, str);
		case LESS_THAN_OR_EQUAL_TO -> wrapper.le(columnName, str);
		case LIKE -> wrapper.like(columnName, str);
		case NOT_LIKE -> wrapper.notLike(columnName, str);
		case START_WITH -> wrapper.likeRight(columnName, str);
		case START_NOT_WITH -> wrapper.notLikeRight(columnName, str);
		case END_WITH -> wrapper.likeLeft(columnName, str);
		case END_NOT_WITH -> wrapper.notLikeLeft(columnName, str);
		case CUSTOM_SQL -> {
			if (customSql == null) {
				break;
			}
			CustomerSqlInfo info = new CustomerSqlInfo(customSql);
			if (info.batch) {
				wrapper.apply(info.sql.replaceAll(CustomerSqlInfo.BATCH_REG, "{0}"), str);
			}
			else if (info.matchFlag) {
				wrapper.apply(info.sql.replaceAll(CustomerSqlInfo.MATCH_REG, "{0}"), str);
			}
			else {
				wrapper.apply(info.sql);
			}
		}
		default -> wrapper.eq(columnName, str);
		}
	}
}
