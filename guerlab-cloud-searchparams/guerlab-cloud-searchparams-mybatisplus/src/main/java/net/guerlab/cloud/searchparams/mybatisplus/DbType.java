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

import java.util.Collections;
import java.util.List;

import jakarta.annotation.Nullable;

/**
 * 数据库类型.
 *
 * @author guer
 */
public enum DbType {

	/**
	 * mysql.
	 */
	MYSQL(List.of("com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.Driver")),
	/**
	 * oracle.
	 */
	ORACLE(List.of("oracle.jdbc.driver.OracleDriver", "oracle.jdbc.OracleDriver")),
	/**
	 * 其他.
	 */
	OTHER(Collections.emptyList());

	private final List<String> driverClassNames;

	DbType(List<String> driverClassNames) {
		this.driverClassNames = driverClassNames;
	}

	@Nullable
	public static DbType byDriverClassName(String driverClassName) {
		for (DbType dbType : DbType.values()) {
			for (String className : dbType.driverClassNames) {
				if (className.equalsIgnoreCase(driverClassName)) {
					return dbType;
				}
			}
		}
		return null;
	}
}
