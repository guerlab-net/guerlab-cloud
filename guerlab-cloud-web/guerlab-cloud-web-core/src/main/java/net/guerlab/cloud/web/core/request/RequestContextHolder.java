/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.web.core.request;

import org.springframework.lang.Nullable;

/**
 * 请求上下文保持.
 *
 * @author guer
 */
public interface RequestContextHolder {

	/**
	 * 获取请求方法.
	 *
	 * @return 请求方法
	 */
	@Nullable
	String getRequestMethod();

	/**
	 * 获取请求路径.
	 *
	 * @return 请求路径
	 */
	@Nullable
	String getRequestPath();

	/**
	 * 获取响应码.
	 *
	 * @return 响应码
	 */
	@Nullable
	Integer getResponseStatusCode();
}
