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

package net.guerlab.cloud.web.webmvc.autoconfigure;

import java.util.ServiceLoader;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.guerlab.cloud.commons.exception.handler.ResponseBuilder;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.web.core.autoconfigure.GlobalExceptionHandlerAutoConfigure;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;
import net.guerlab.cloud.web.webmvc.exception.handler.WebMvcGlobalExceptionHandler;

/**
 * 异常统一处理配置自动配置.
 *
 * @author guer
 */
@Configuration
@AutoConfigureAfter(GlobalExceptionHandlerAutoConfigure.class)
public class WebMvcGlobalExceptionHandlerAutoConfigure {

	/**
	 * 异常统一处理配置.
	 *
	 * @author guer
	 */
	@SuppressWarnings("unused")
	@RestControllerAdvice
	public static class DefaultWebMvcGlobalExceptionHandler extends WebMvcGlobalExceptionHandler {

		/**
		 * 初始化异常统一处理配置.
		 *
		 * @param messageSource
		 *         messageSource
		 * @param stackTracesHandler
		 *         堆栈处理
		 * @param globalExceptionLogger
		 *         全局异常处理日志记录器
		 * @param globalExceptionProperties
		 *         全局异常处理配置
		 */
		public DefaultWebMvcGlobalExceptionHandler(MessageSource messageSource, StackTracesHandler stackTracesHandler,
				GlobalExceptionLogger globalExceptionLogger, GlobalExceptionProperties globalExceptionProperties) {
			super(messageSource, stackTracesHandler, globalExceptionLogger,
					ServiceLoader.load(ResponseBuilder.class).stream().map(ServiceLoader.Provider::get)
							.collect(Collectors.toList()), globalExceptionProperties);
		}
	}
}
