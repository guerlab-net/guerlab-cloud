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

import java.util.List;

import lombok.Data;

/**
 * 切面配置.
 *
 * @author guer
 */
@Data
public class MultiDataSourceAdvisorProperties {

	/**
	 * 基础包路径列表.
	 */
	private List<String> basePackages;

	/**
	 * 切入点配置列表.
	 */
	private List<PointcutProperties> pointcuts;
}
