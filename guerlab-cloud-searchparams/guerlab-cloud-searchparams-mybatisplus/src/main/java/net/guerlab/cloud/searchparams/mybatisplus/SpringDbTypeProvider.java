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

import java.util.List;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import net.guerlab.cloud.core.util.SpringUtils;

/**
 * 基于Spring上下文数据库类型提供器.
 *
 * @author guer
 */
@Slf4j
public class SpringDbTypeProvider implements DbTypeProvider {

	@Nullable
	@Override
	public DbType getDbType(Object object, List<DbType> dbTypes) {
		DataSourceProperties properties = null;
		try {
			properties = SpringUtils.getBean(DataSourceProperties.class);
		}
		catch (Exception e) {
			log.debug("get DataSourceProperties bean fail: {}", e.getMessage());
		}

		if (properties == null) {
			return null;
		}

		for (DbType dbType : dbTypes) {
			if (dbType.driverClassNames().contains(properties.getDriverClassName())) {
				return dbType;
			}
		}

		return null;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
