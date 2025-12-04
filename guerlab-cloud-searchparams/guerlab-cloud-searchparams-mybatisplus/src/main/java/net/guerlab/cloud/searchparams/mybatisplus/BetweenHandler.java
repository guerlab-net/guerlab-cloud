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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import net.guerlab.cloud.searchparams.Between;
import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * 排序类型处理.
 *
 * @author guer
 */
@Slf4j
public class BetweenHandler extends AbstractMyBatisPlusSearchParamsHandler {

	@Override
	public Class<?> acceptClass() {
		return Between.class;
	}

	@Override
	public void setValue(Object object, Field field, String columnName, Object value,
			SearchModelType searchModelType, @Nullable String customSql, @Nullable JsonField jsonField) {
		Between<?> between = (Between<?>) value;
		if (between.isInvalid()) {
			return;
		}

		QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
		columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());

		if (jsonField != null) {
			log.warn("SearchModelType.BETWEEN cannot support json field");
		}
		else {
			wrapper.between(columnName, between.getStart(), between.getEnd());
		}
	}
}
