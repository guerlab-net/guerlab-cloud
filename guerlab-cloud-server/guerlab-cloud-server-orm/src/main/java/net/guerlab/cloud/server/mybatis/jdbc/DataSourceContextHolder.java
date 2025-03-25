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

package net.guerlab.cloud.server.mybatis.jdbc;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

/**
 * 数据源上下文保持.
 *
 * @author guer
 */
@Slf4j
public class DataSourceContextHolder {

	/**
	 * 当前数据源.
	 */
	private final ThreadLocal<String> currentSource = new ThreadLocal<>();

	/**
	 * 默认数据源.
	 */
	private final String defaultSource;

	/**
	 * 根据默认数据源创建数据源上下文保持.
	 *
	 * @param defaultSource 默认数据源
	 */
	public DataSourceContextHolder(String defaultSource) {
		Assert.hasText(defaultSource, "default source cannot be empty");
		this.defaultSource = defaultSource;
	}

	/**
	 * 获取数据源.
	 */
	public String getDataSource() {
		String current = currentSource.get();
		if (current == null) {
			current = defaultSource;
		}
		return current;
	}

	/**
	 * 设置数据源.
	 *
	 * @param type 数据源
	 */
	public void setDataSource(String type) {
		log.debug("change datasource to {}", type);
		currentSource.set(type);
	}

	/**
	 * 清理数据源.
	 */
	public void clearDataSource() {
		currentSource.remove();
	}

}
