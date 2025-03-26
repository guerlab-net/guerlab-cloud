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

package net.guerlab.cloud.server.jdbc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 多数据源切面.
 *
 * @author guer
 */
public class MultiDataSourceAdvice implements MethodInterceptor {

	private final DataSourceContextHolder dataSourceContextHolder;

	private final String dataSource;

	public MultiDataSourceAdvice(DataSourceContextHolder dataSourceContextHolder, String dataSource) {
		this.dataSourceContextHolder = dataSourceContextHolder;
		this.dataSource = dataSource;
	}

	@Nullable
	@Override
	public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
		dataSourceContextHolder.setDataSource(dataSource);
		try {
			return invocation.proceed();
		}
		finally {
			dataSourceContextHolder.clearDataSource();
		}
	}
}
