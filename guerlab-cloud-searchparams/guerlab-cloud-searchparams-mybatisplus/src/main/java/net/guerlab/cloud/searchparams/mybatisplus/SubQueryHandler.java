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

package net.guerlab.cloud.searchparams.mybatisplus;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.searchparams.SearchParamsUtils;
import net.guerlab.cloud.searchparams.SubQuerySupport;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 子查询处理.
 *
 * @author guer
 */
@Slf4j
public class SubQueryHandler extends AbstractMyBatisPlusSearchParamsHandler {

	private static final AtomicInteger SUB_QUERY_ATOMIC = new AtomicInteger();

	/**
	 * 重置子查询计数.
	 */
	public static void resetSubQueryAtomic() {
		SUB_QUERY_ATOMIC.set(0);
	}

	@Override
	public Class<?> acceptClass() {
		return SearchParams.class;
	}

	@Override
	public void setValue(Object object, Field field, String columnName, Object value,
			SearchModelType searchModelType, @Nullable String customSql, @Nullable JsonField jsonField) {
		SubQuerySupport subQuerySupport = field.getAnnotation(SubQuerySupport.class);
		if (subQuerySupport == null) {
			return;
		}
		String resultFieldName = StringUtils.trimToNull(subQuerySupport.resultFieldName());
		String refType = StringUtils.trimToNull(subQuerySupport.refType());
		if (refType == null) {
			return;
		}
		Class<?> entityClass = subQuerySupport.entityClass();

		String tableName = StringUtils.trimToNull(subQuerySupport.tableName());
		if (tableName == null) {
			TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
			if (tableInfo != null) {
				tableName = tableInfo.getTableName();
			}
		}
		if (tableName == null) {
			log.warn("subQuery miss tableName, entityClass is {}", entityClass.getName());
			return;
		}

		String baseWhere = StringUtils.trimToNull(subQuerySupport.baseWhere());
		String subQuerySql = "SELECT %S FROM %s".formatted(resultFieldName, tableName);

		QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
		QueryWrapper<?> subQueryWrapper = new QueryWrapper<>(entityClass);
		SearchParamsUtils.handler((SearchParams) value, subQueryWrapper);

		String sqlSegment = subQueryWrapper.getSqlSegment();
		Map<String, Object> valuePairs = subQueryWrapper.getParamNameValuePairs();
		if (!StringUtils.isBlank(sqlSegment) && !CollectionUtil.isEmpty(valuePairs)) {
			String subQueryKind = "subQuery" + SUB_QUERY_ATOMIC.incrementAndGet();
			sqlSegment = sqlSegment.replace("#{ew.paramNameValuePairs.MPGENVAL", "#{ew.paramNameValuePairs." + subQueryKind + "_MPGENVAL");

			valuePairs.forEach((k, v) -> wrapper.getParamNameValuePairs().put(subQueryKind + "_" + k, v));

			if (baseWhere != null) {
				subQuerySql += " WHERE " + baseWhere + " AND " + sqlSegment;
			}
			else {
				subQuerySql += " WHERE " + sqlSegment;
			}
		}
		else if (baseWhere != null) {
			subQuerySql += " WHERE " + baseWhere;
		}

		columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());
		wrapper.apply("%s %s (%s)".formatted(columnName, refType, subQuerySql));
	}
}
