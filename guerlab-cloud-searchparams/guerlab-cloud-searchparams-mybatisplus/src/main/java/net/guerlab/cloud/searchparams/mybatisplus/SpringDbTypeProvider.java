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

import jakarta.annotation.Nullable;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 基于Spring上下文数据库类型提供器.
 *
 * @author guer
 */
public class SpringDbTypeProvider implements DbTypeProvider, ApplicationContextAware {

	private static ApplicationContext context = null;

	@Nullable
	@Override
	public DbType getDbType(Object object) {
		if (context == null) {
			return null;
		}

		DataSourceProperties dataSourceProperties = null;
		try {
			dataSourceProperties = context.getBean(DataSourceProperties.class);
		}
		catch (Exception ignored) {
		}

		if (dataSourceProperties == null) {
			return null;
		}

		return DbType.byDriverClassName(dataSourceProperties.getDriverClassName());
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
