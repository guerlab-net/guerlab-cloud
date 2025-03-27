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

import java.util.List;
import java.util.Map;

import lombok.Data;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 多数据源配置.
 *
 * @author guer
 */
@Data
@ConfigurationProperties(prefix = MultiDataSourceProperties.PREFIX)
public class MultiDataSourceProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PREFIX = "spring.multi-datasource";

	/**
	 * 是否启用多数据源支持.
	 */
	private boolean enabled;

	/**
	 * 默认数据源.
	 */
	private String defaultDataSource;

	/**
	 * 数据源表.
	 */
	private Map<String, DataSourceProperties> sources;

	/**
	 * 切入点配置列表.
	 */
	private List<PointcutProperties> pointcuts;

}
