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

package net.guerlab.cloud.gateway.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

/**
 * Url定义.
 *
 * @author guer
 */
@Setter
@Getter
public class UrlDefinition {

	private static final AntPathMatcher MATCHER = new AntPathMatcher();

	/**
	 * 请求方式.
	 */
	private HttpMethod method;

	/**
	 * 路径.
	 */
	private String path;

	@Override
	public String toString() {
		return method + " " + path;
	}

	/**
	 * 是否为空.
	 *
	 * @return 为空
	 */
	@JsonIgnore
	public boolean isEmpty() {
		if (method != null) {
			return false;
		}
		return StringUtils.isBlank(path);
	}

	/**
	 * 判断是否匹配.
	 *
	 * @param requestMethod 请求方法
	 * @param requestPath   请求路径
	 * @return 是否匹配
	 */
	public boolean match(String requestMethod, String requestPath) {
		if (isEmpty()) {
			return false;
		}
		String currentPath = StringUtils.trimToNull(getPath());

		boolean pathMatch = currentPath == null || MATCHER.match(currentPath, requestPath);
		boolean methodMatch = method == null || method.matches(requestMethod);
		return pathMatch && methodMatch;
	}
}
