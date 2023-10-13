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

package net.guerlab.cloud.stream;

import java.util.Collections;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态方法配置.
 *
 * @author guer
 */
@Data
@ConfigurationProperties(prefix = DynamicsFunctionProperties.PROPERTIES_PREFIX)
public class DynamicsFunctionProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = "spring.cloud.function.dynamics";

	/**
	 * 定义列表.
	 */
	private List<DynamicsFunctionDefinition> definitions = Collections.emptyList();
}
