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

package net.guerlab.cloud.web.core.exception.handler;

import java.util.Collection;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;

import net.guerlab.cloud.commons.exception.handler.ResponseBuilder;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.core.result.Fail;

/**
 * 异常统一处理配置.
 *
 * @author guer
 */
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * messageSource.
	 */
	protected final MessageSource messageSource;

	/**
	 * 堆栈处理.
	 */
	protected final StackTracesHandler stackTracesHandler;

	/**
	 * 全局异常处理日志记录器.
	 */
	@Getter
	protected final GlobalExceptionLogger globalExceptionLogger;

	/**
	 * 异常信息构建者列表.
	 */
	protected final Collection<ResponseBuilder> builders;

	/**
	 * 通用异常处理.
	 */
	private final ThrowableResponseBuilder defaultBuilder;

	/**
	 * 创建异常统一处理配置.
	 *
	 * @param messageSource         messageSource
	 * @param stackTracesHandler    堆栈处理
	 * @param globalExceptionLogger 全局异常处理日志记录器
	 * @param builders              异常信息构建者列表
	 * @param defaultBuilder        通用异常处理
	 */
	public GlobalExceptionHandler(MessageSource messageSource,
			StackTracesHandler stackTracesHandler,
			GlobalExceptionLogger globalExceptionLogger,
			Collection<ResponseBuilder> builders,
			ThrowableResponseBuilder defaultBuilder) {
		this.messageSource = messageSource;
		this.stackTracesHandler = stackTracesHandler;
		this.globalExceptionLogger = globalExceptionLogger;
		this.builders = builders;
		this.defaultBuilder = defaultBuilder;

		defaultBuilder.setStackTracesHandler(stackTracesHandler);
		defaultBuilder.setMessageSource(messageSource);

		builders.forEach(builder -> {
			builder.setStackTracesHandler(stackTracesHandler);
			builder.setMessageSource(messageSource);
		});
	}

	/**
	 * 异常信息构建处理.
	 *
	 * @param e 异常
	 * @return 异常信息
	 */
	public Fail<?> build(Throwable e) {
		log.debug("catch exception: {}", e.getClass());
		ResponseBuilder responseBuilder = builders.stream().sorted().filter(builder -> builder.accept(e)).findFirst()
				.orElse(defaultBuilder);
		log.debug("use ResponseBuilder: {}", responseBuilder);
		return responseBuilder.build(e);
	}
}
