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

package net.guerlab.cloud.searchparams.mybatisplus.dbtype;

import java.util.List;

import jakarta.annotation.Nullable;

import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.mybatisplus.DbType;

/**
 * 未知.
 *
 * @author guer
 */
public final class Unknown implements DbType {

	/**
	 * 实例.
	 */
	public static final DbType INSTANCE = new Unknown();

	private Unknown() {
	}

	@Override
	public List<String> driverClassNames() {
		return List.of();
	}

	@Nullable
	@Override
	public String formatJsonQuerySql(String columnName, SearchModelType searchModelType, String jsonPath, int size) {
		return null;
	}
}
