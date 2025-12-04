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

import java.util.List;

import lombok.Data;

/**
 * 动态数据源切面配置.
 *
 * @author guer
 */
@Data
public class PointcutProperties {

	/**
	 * 数据源.
	 */
	private String dataSource;

	/**
	 * 类名称匹配列表.
	 */
	private List<String> classNameMatch;

	/**
	 * 方法名称匹配列表.
	 */
	private List<String> methodNameMatch;
}
