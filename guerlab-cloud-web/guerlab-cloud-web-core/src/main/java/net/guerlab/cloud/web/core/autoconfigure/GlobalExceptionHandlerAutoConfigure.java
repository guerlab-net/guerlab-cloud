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

package net.guerlab.cloud.web.core.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.web.core.exception.handler.DefaultGlobalExceptionLogger;
import net.guerlab.cloud.web.core.exception.handler.DefaultStackTracesHandler;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.exception.handler.ThrowableResponseBuilder;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;

/**
 * 异常统一处理配置自动配置.
 *
 * @author guer
 */
@AutoConfiguration
@EnableConfigurationProperties(GlobalExceptionProperties.class)
public class GlobalExceptionHandlerAutoConfigure {

	/**
	 * 构建 堆栈处理.
	 *
	 * @param properties 全局异常处理配置
	 * @return 堆栈处理
	 */
	@Bean
	@ConditionalOnMissingBean
	public StackTracesHandler stackTracesHandler(GlobalExceptionProperties properties) {
		return new DefaultStackTracesHandler(properties);
	}

	/**
	 * 构建 全局异常处理日志记录器.
	 *
	 * @param properties 全局异常处理配置
	 * @return 全局异常处理日志记录器
	 */
	@Bean
	@ConditionalOnMissingBean
	public GlobalExceptionLogger globalExceptionLogger(GlobalExceptionProperties properties) {
		return new DefaultGlobalExceptionLogger(properties);
	}

	/**
	 * 构建 通用异常处理.
	 *
	 * @param properties 全局异常处理配置
	 * @return 通用异常处理
	 */
	@Bean
	@ConditionalOnMissingBean
	public ThrowableResponseBuilder throwableResponseBuilder(GlobalExceptionProperties properties) {
		return new ThrowableResponseBuilder(properties);
	}
}
