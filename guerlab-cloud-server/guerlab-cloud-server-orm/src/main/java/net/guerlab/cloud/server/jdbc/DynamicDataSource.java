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

package net.guerlab.cloud.server.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源.
 *
 * @author guer
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 数据源上下文保持.
	 */
	private final DataSourceContextHolder dataSourceContextHolder;

	/**
	 * 使用数据源上下文保持创建动态数据源.
	 *
	 * @param dataSourceContextHolder 数据源上下文保持
	 */
	public DynamicDataSource(DataSourceContextHolder dataSourceContextHolder) {
		this.dataSourceContextHolder = dataSourceContextHolder;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceContextHolder.getDataSource();
	}

}
