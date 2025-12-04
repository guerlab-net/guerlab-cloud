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

package net.guerlab.cloud.commons.ip;

import jakarta.annotation.Nullable;

/**
 * ip地址解析器.
 *
 * @author guer
 */
public interface IpParser {

	/**
	 * 判断是否支持对该请求对象的处理.
	 *
	 * @param request 请求对象
	 * @return 是否允许处理
	 */
	boolean accept(Object request);

	/**
	 * 从请求头获取IP地址.
	 *
	 * @param request    请求对象
	 * @param headerName 请求头名称
	 * @return IP地址
	 */
	@Nullable
	String getIpByHeader(Object request, String headerName);

	/**
	 * 从远端地址获取IP地址.
	 *
	 * @param request 请求对象
	 * @return IP地址
	 */
	@Nullable
	String getIpByRemoteAddress(Object request);
}
