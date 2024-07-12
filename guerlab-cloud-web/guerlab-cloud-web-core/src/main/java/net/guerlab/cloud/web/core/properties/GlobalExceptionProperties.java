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

package net.guerlab.cloud.web.core.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

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

	private final AntPathMatcher matcher = new AntPathMatcher();

	/**
	 * 通用匹配符.
	 */
	private static final String ALL = "*";

	/**
	 * 异常处理返回状态码.
	 */
	private int statusCode = 500;

	/**
	 * 路径响应码列表.
	 */
	private List<UrlResponseCode> statusCodes = new ArrayList<>();

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
	 * 是否重写非应用异常.
	 */
	private boolean rewriteNonApplicationExceptions;

	/**
	 * 全局异常处理日志记录器日志级别.
	 */
	private LogLevel globalExceptionLoggerLevel = LogLevel.DEBUG;

	/**
	 * 重写异常信息.
	 */
	private Map<String, String> overrideException = new HashMap<>();

	/**
	 * 根据异常信息类获取覆写异常模板.
	 *
	 * @param throwableClass 异常信息类
	 * @return 覆写异常模板
	 */
	@Nullable
	public String getOverrideExceptionTemplate(Class<? extends Throwable> throwableClass) {
		if (overrideException == null || overrideException.isEmpty()) {
			return null;
		}
		return overrideException.get(throwableClass.getName());
	}

	/**
	 * 设置覆写异常模板.
	 *
	 * @param throwableClass 异常信息类
	 * @param template       覆写异常模板
	 */
	public void addOverrideExceptionTemplate(Class<? extends Throwable> throwableClass, String template) {
		template = StringUtils.trimToNull(template);
		if (template == null) {
			return;
		}

		if (overrideException == null) {
			overrideException = new HashMap<>();
		}

		overrideException.put(throwableClass.getName(), template);
	}

	/**
	 * 判断是否在排除列表中匹配.
	 *
	 * @param methodKey 方法签名
	 * @return 是否匹配
	 */
	@Override
	public boolean excludeMatch(String methodKey) {
		return match(stackTraceExcludes, methodKey);
	}

	/**
	 * 判断是否在包含列表中匹配.
	 *
	 * @param methodKey 方法签名
	 * @return 是否匹配
	 */
	@Override
	public boolean includeMatch(String methodKey) {
		return match(stackTraceIncludes, methodKey);
	}

	/**
	 * 判断是否在列表中匹配.
	 *
	 * @param list      列表
	 * @param methodKey 方法签名
	 * @return 是否匹配
	 */
	private boolean match(List<String> list, String methodKey) {
		return list.contains(ALL) || list.parallelStream().anyMatch(methodKey::startsWith);
	}

	/**
	 * 获取响应码.
	 *
	 * @param requestMethod 请求方法
	 * @param requestPath   请求路径
	 * @return 响应码.
	 */
	public int getStatusCode(String requestMethod, String requestPath) {
		int code = getStatusCode();
		if (statusCodes != null && !statusCodes.isEmpty()) {
			UrlResponseCode urlResponseCode = statusCodes.stream()
					.filter(url -> urlResponseCodeMatch(requestMethod, requestPath, url)).findFirst()
					.orElse(null);
			if (urlResponseCode != null) {
				code = urlResponseCode.getStatusCode();
			}
		}
		return code;
	}

	/**
	 * 获取首条匹配的路径响应码.
	 *
	 * @param requestMethod 请求方法
	 * @param requestPath   请求路径
	 * @return 路径响应码.
	 */
	@Nullable
	public UrlResponseCode getFirstMatchUrlResponseCode(String requestMethod, String requestPath) {
		if (statusCodes == null || statusCodes.isEmpty()) {
			return null;
		}
		return statusCodes.stream().filter(url -> urlResponseCodeMatch(requestMethod, requestPath, url)).findFirst()
				.orElse(null);
	}

	private boolean urlResponseCodeMatch(String requestMethod, String requestPath, UrlResponseCode url) {
		String path = StringUtils.trimToNull(url.getPath());
		if (path == null) {
			return false;
		}
		HttpMethod method = url.getMethod();
		if (method != null && !method.matches(requestMethod)) {
			return false;
		}
		return matcher.match(path, requestPath);
	}

	/**
	 * 路径.
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

	/**
	 * 路径响应码.
	 */
	@Setter
	@Getter
	public static class UrlResponseCode extends Url {

		/**
		 * 异常处理返回状态码.
		 */
		private int statusCode = 500;
	}
}
