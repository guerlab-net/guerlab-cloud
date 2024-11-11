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

package net.guerlab.cloud.stream;

import lombok.Data;

import org.springframework.context.ApplicationEvent;

/**
 * 动态函数定义.
 *
 * @author guer
 */
@Data
public class DynamicsFunctionDefinition {

	/**
	 * 方法名.
	 */
	private String functionName;

	/**
	 * 输入类型.
	 */
	private Class<?> inputClass;

	/**
	 * 事件类型.
	 */
	private Class<? extends ApplicationEvent> eventClass;
}
