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

package net.guerlab.cloud.searchparams.mybatisplus.dbtype;

import java.util.ArrayList;
import java.util.List;

import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.mybatisplus.DbType;

/**
 * Mysql.
 *
 * @author guer
 */
public class Mysql implements DbType {

	@Override
	public List<String> driverClassNames() {
		return List.of("com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.Driver");
	}

	@Override
	public String formatJsonQuerySql(String columnName, SearchModelType searchModelType, String jsonPath, int size) {
		boolean isNotIn = searchModelType == SearchModelType.NOT_IN;
		List<String> sqlList = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			if (isNotIn) {
				sqlList.add("JSON_SEARCH(%s, 'one', {%s}, null, '%s') IS NULL".formatted(columnName, i, jsonPath));
			}
			else {
				sqlList.add("JSON_SEARCH(%s, 'one', {%s}, null, '%s') IS NOT NULL".formatted(columnName, i, jsonPath));
			}
		}
		return String.join(isNotIn ? " AND " : " OR ", sqlList);
	}
}
