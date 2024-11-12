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

import jakarta.annotation.Nullable;

import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * 数据库类型.
 *
 * @author guer
 */
public interface DbType {

	/**
	 * 驱动类列表.
	 *
	 * @return 驱动类列表
	 */
	List<String> driverClassNames();

	/**
	 * 格式化json查询sql.
	 *
	 * @param columnName      字段名称
	 * @param searchModelType 查询模式
	 * @param jsonPath        json路径
	 * @param size            查询数量
	 * @return 格式化后的sql
	 */
	@Nullable
	String formatJsonQuerySql(String columnName, SearchModelType searchModelType, String jsonPath, int size);

	/**
	 * json查询值格式化.
	 *
	 * @param value           待查询值
	 * @param searchModelType 查询模式
	 * @param jsonPath        json路径
	 * @return 格式化后值
	 */
	default Object jsonQueryValueFormat(Object value, SearchModelType searchModelType, String jsonPath) {
		return value;
	}
}
