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

package net.guerlab.cloud.security.core;

import java.util.List;

import jakarta.annotation.Nullable;

import org.springframework.http.HttpMethod;

/**
 * 授权路径提供者.
 *
 * @author guer
 */
public interface AuthorizePathProvider {

	/**
	 * 请求方式.
	 *
	 * @return 请求方式
	 */
	@Nullable
	HttpMethod httpMethod();

	/**
	 * 路径列表.
	 *
	 * @return 路径列表
	 */
	List<String> paths();
}
