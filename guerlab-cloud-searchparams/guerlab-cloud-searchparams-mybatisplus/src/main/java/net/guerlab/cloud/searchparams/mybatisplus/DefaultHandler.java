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

package net.guerlab.cloud.searchparams.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsHandler;

/**
 * 默认处理.
 *
 * @author guer
 */
public class DefaultHandler implements SearchParamsHandler {

	@Override
	public void setValue(Object object, String fieldName, String columnName, Object value,
			SearchModelType searchModelType, @Nullable String customSql) {
		QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
		columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());
		switch (searchModelType) {
		case IS_NULL -> wrapper.isNull(columnName);
		case IS_NOT_NULL -> wrapper.isNotNull(columnName);
		case GREATER_THAN -> wrapper.gt(columnName, value);
		case GREATER_THAN_OR_EQUAL_TO, START_WITH -> wrapper.ge(columnName, value);
		case LESS_THAN -> wrapper.lt(columnName, value);
		case LESS_THAN_OR_EQUAL_TO, END_WITH -> wrapper.le(columnName, value);
		case NOT_EQUAL_TO, NOT_LIKE, START_NOT_WITH, END_NOT_WITH -> wrapper.ne(columnName, value);
		case CUSTOM_SQL -> {
			if (customSql == null) {
				break;
			}
			CustomerSqlInfo info = new CustomerSqlInfo(customSql);
			if (info.batch) {
				wrapper.apply(info.sql.replaceAll(CustomerSqlInfo.BATCH_REG, "{0}"), value);
			}
			else if (info.matchFlag) {
				wrapper.apply(info.sql.replaceAll(CustomerSqlInfo.MATCH_REG, "{0}"), value);
			}
			else {
				wrapper.apply(info.sql);
			}
		}
		default -> wrapper.eq(columnName, value);
		}
	}
}
