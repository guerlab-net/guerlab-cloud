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

package net.guerlab.cloud.web.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;

/**
 * 默认全局异常处理日志记录器.
 *
 * @author guer
 */
@Slf4j
public class DefaultGlobalExceptionLogger implements GlobalExceptionLogger {

	private final AntPathMatcher matcher = new AntPathMatcher();

	private final GlobalExceptionProperties properties;

	/**
	 * 创建默认全局异常处理日志记录器.
	 *
	 * @param properties 全局异常处理配置
	 */
	public DefaultGlobalExceptionLogger(GlobalExceptionProperties properties) {
		this.properties = properties;
	}

	@Override
	public void debug(Throwable e, String requestMethod, String requestPath) {
		properties.getLogIgnorePaths().forEach(url -> debugOnce(e, requestMethod, requestPath, url));
	}

	private void debugOnce(Throwable e, String requestMethod, String requestPath, GlobalExceptionProperties.Url url) {
		String path = StringUtils.trimToNull(url.getPath());
		if (path == null) {
			return;
		}
		HttpMethod method = url.getMethod();
		if (method != null && method.matches(requestMethod)) {
			return;
		}
		if (matcher.match(path, requestPath)) {
			return;
		}

		String message = String.format("request uri[%s %s]", requestMethod, requestPath);

		LogLevel logLevel = properties.getGlobalExceptionLoggerLevel();
		if (logLevel != null) {
			switch (logLevel) {
			case OFF -> {
			}
			case TRACE -> log.trace(message, e);
			case INFO -> log.info(message, e);
			case WARN -> log.warn(message, e);
			case ERROR -> log.error(message, e);
			default -> log.debug(message, e);
			}
		}
		else {
			log.debug(message, e);
		}
	}
}
