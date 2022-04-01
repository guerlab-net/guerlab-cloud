/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.StringUtils;

import org.springframework.lang.Nullable;

import net.guerlab.commons.reflection.FieldUtil;

/**
 * 字段名称获取.
 *
 * @author guer
 */
public final class ColumnNameGetter {

	private ColumnNameGetter() {

	}

	/**
	 * 获取字段名.
	 *
	 * @param columnName  字段名称
	 * @param entityClass 实体类类型
	 * @return 字段名
	 */
	public static String getColumnName(String columnName, @Nullable Class<?> entityClass) {
		if (entityClass == null) {
			return columnName;
		}

		Field field = FieldUtil.getField(entityClass, columnName);
		if (field == null) {
			return columnName;
		}

		TableField tableField = field.getAnnotation(TableField.class);
		if (tableField != null) {
			String tableFieldValue = StringUtils.trimToNull(tableField.value());
			if (tableFieldValue != null) {
				return tableFieldValue;
			}
		}

		TableId tableId = field.getAnnotation(TableId.class);
		if (tableId != null) {
			String tableIdValue = StringUtils.trimToNull(tableId.value());
			if (tableIdValue != null) {
				return tableIdValue;
			}
		}

		return columnName;
	}
}
