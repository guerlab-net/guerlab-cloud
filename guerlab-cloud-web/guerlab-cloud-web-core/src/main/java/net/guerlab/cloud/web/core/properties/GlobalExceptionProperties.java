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

package net.guerlab.cloud.web.core.properties;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

import net.guerlab.cloud.commons.config.GlobalExceptionConfig;

/**
 * 全局异常处理配置.
 *
 * @author guer
 */
@Setter
@Getter
@RefreshScope
@ConfigurationProperties("spring.global-exception")
public class GlobalExceptionProperties implements GlobalExceptionConfig {

	/**
	 * 通用匹配符.
	 */
	private static final String ALL = "*";

	/**
	 * 是否打印堆栈根据.
	 */
	private boolean printStackTrace;

	/**
	 * 堆栈跟踪排除列表.
	 */
	private List<String> stackTraceExcludes = new ArrayList<>();

	/**
	 * 堆栈跟踪包含列表.
	 */
	private List<String> stackTraceIncludes = new ArrayList<>();

	/**
	 * 日志忽略路径列表.
	 */
	private List<Url> logIgnorePaths = new ArrayList<>();

	/**
	 * 判断是否在排除列表中匹配.
	 *
	 * @param methodKey
	 *         方法签名
	 * @return 是否匹配
	 */
	@Override
	public boolean excludeMatch(String methodKey) {
		return match(stackTraceExcludes, methodKey);
	}

	/**
	 * 判断是否在包含列表中匹配.
	 *
	 * @param methodKey
	 *         方法签名
	 * @return 是否匹配
	 */
	@Override
	public boolean includeMatch(String methodKey) {
		return match(stackTraceIncludes, methodKey);
	}

	/**
	 * 判断是否在列表中匹配.
	 *
	 * @param list
	 *         列表
	 * @param methodKey
	 *         方法签名
	 * @return 是否匹配
	 */
	private boolean match(List<String> list, String methodKey) {
		return list.contains(ALL) || list.parallelStream().anyMatch(methodKey::startsWith);
	}

	/**
	 * 忽略路径.
	 */
	@Setter
	@Getter
	public static class Url {

		/**
		 * 请求方式.
		 */
		@Nullable
		private HttpMethod method;

		/**
		 * 路径.
		 */
		@Nullable
		private String path;
	}
}
