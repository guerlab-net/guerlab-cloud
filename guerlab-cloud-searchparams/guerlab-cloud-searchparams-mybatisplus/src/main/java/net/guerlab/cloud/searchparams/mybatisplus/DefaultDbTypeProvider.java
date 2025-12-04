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

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.List;

import jakarta.annotation.Nullable;

import org.springframework.core.Ordered;

/**
 * 默认数据库类型提供器.
 *
 * @author guer
 */
public class DefaultDbTypeProvider implements DbTypeProvider {

	@Nullable
	@Override
	public DbType getDbType(Object object, List<DbType> dbTypes) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			String className = driver.getClass().getName();
			for (DbType dbType : dbTypes) {
				if (dbType.driverClassNames().contains(className)) {
					return dbType;
				}
			}
		}
		return null;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
