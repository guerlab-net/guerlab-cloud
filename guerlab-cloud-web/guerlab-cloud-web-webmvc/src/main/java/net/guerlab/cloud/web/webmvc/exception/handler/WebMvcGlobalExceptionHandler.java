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

package net.guerlab.cloud.web.webmvc.exception.handler;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import net.guerlab.cloud.commons.exception.handler.ResponseBuilder;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;
import net.guerlab.cloud.web.core.request.RequestHolder;

/**
 * 异常统一处理配置.
 *
 * @author guer
 */
@Slf4j
public class WebMvcGlobalExceptionHandler extends GlobalExceptionHandler {

	private final GlobalExceptionProperties globalExceptionProperties;

	/**
	 * 初始化异常统一处理配置.
	 *
	 * @param messageSource             messageSource
	 * @param stackTracesHandler        堆栈处理
	 * @param globalExceptionLogger     全局异常处理日志记录器
	 * @param builders                  异常信息构建者列表
	 * @param globalExceptionProperties 全局异常处理配置
	 */
	public WebMvcGlobalExceptionHandler(MessageSource messageSource, StackTracesHandler stackTracesHandler,
			GlobalExceptionLogger globalExceptionLogger, Collection<ResponseBuilder> builders,
			GlobalExceptionProperties globalExceptionProperties) {
		super(messageSource, stackTracesHandler, globalExceptionLogger, builders);
		this.globalExceptionProperties = globalExceptionProperties;
	}

	/**
	 * 异常处理.
	 *
	 * @param e 异常
	 * @return 响应数据
	 */
	protected ResponseEntity<Fail<?>> exceptionHandler0(Exception e) {
		String requestMethod = RequestHolder.getRequestMethod();
		String requestPath = RequestHolder.getRequestPath();
		Integer responseStatusCode = RequestHolder.responseStatusCode();
		globalExceptionLogger.debug(e, requestMethod, requestPath);

		int statusCode = responseStatusCode != null ? responseStatusCode : globalExceptionProperties.getStatusCode(requestMethod, requestPath);
		return ResponseEntity.status(statusCode).body(build(e));
	}

}
