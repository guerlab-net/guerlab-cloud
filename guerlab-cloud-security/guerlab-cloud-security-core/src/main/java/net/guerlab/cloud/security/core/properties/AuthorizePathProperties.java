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

package net.guerlab.cloud.security.core.properties;

import java.util.Collections;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 授权保护路径配置.
 *
 * @author guer
 */
@Data
@ConfigurationProperties(prefix = "spring.security.paths")
public class AuthorizePathProperties {

	/**
	 * 所有请求方式路径列表.
	 */
	private List<String> all = Collections.emptyList();

	/**
	 * GET请求方式路径列表.
	 */
	private List<String> get = Collections.emptyList();

	/**
	 * HEAD请求方式路径列表.
	 */
	private List<String> head = Collections.emptyList();

	/**
	 * POST请求方式路径列表.
	 */
	private List<String> post = Collections.emptyList();

	/**
	 * PUT请求方式路径列表.
	 */
	private List<String> put = Collections.emptyList();

	/**
	 * PATCH请求方式路径列表.
	 */
	private List<String> patch = Collections.emptyList();

	/**
	 * DELETE请求方式路径列表.
	 */
	private List<String> delete = Collections.emptyList();

	/**
	 * OPTIONS请求方式路径列表.
	 */
	private List<String> options = Collections.emptyList();

	/**
	 * TRACE请求方式路径列表.
	 */
	private List<String> trace = Collections.emptyList();

}
