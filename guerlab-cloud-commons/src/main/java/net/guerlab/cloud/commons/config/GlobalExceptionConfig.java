/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.config;

/**
 * 全局异常处理配置.
 *
 * @author guer
 */
public interface GlobalExceptionConfig {

	/**
	 * 是否打印异常堆栈.
	 *
	 * @return 打印异常堆栈
	 */
	boolean isPrintStackTrace();

	/**
	 * 判断是否在排除列表中匹配.
	 *
	 * @param methodKey
	 *         方法签名
	 * @return 是否匹配
	 */
	boolean excludeMatch(String methodKey);

	/**
	 * 判断是否在包含列表中匹配.
	 *
	 * @param methodKey
	 *         方法签名
	 * @return 是否匹配
	 */
	boolean includeMatch(String methodKey);
}
