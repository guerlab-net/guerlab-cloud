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

package net.guerlab.cloud.web.webflux.autoconfigure;

import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import net.guerlab.cloud.commons.exception.handler.ResponseBuilder;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.web.core.autoconfigure.GlobalExceptionHandlerAutoConfigure;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionLogger;
import net.guerlab.cloud.web.core.exception.handler.ThrowableResponseBuilder;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;
import net.guerlab.cloud.web.webflux.exception.handler.WebFluxErrorWebExceptionHandler;

/**
 * 异常统一处理配置自动配置.
 *
 * @author guer
 */
@AutoConfiguration(after = GlobalExceptionHandlerAutoConfigure.class)
public class WebFluxGlobalExceptionHandlerAutoConfigure {

	private final ServerProperties serverProperties;

	private final WebProperties.Resources resourceProperties;

	private final List<ViewResolver> viewResolvers;

	private final ServerCodecConfigurer serverCodecConfigurer;

	/**
	 * 创建异常统一处理配置自动配置.
	 *
	 * @param serverProperties      ServerProperties
	 * @param webProperties         WebProperties
	 * @param viewResolvers         ViewResolver列表
	 * @param serverCodecConfigurer ServerCodecConfigurer
	 */
	public WebFluxGlobalExceptionHandlerAutoConfigure(ServerProperties serverProperties, WebProperties webProperties,
			List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
		this.serverProperties = serverProperties;
		this.resourceProperties = webProperties.getResources();
		this.viewResolvers = viewResolvers;
		this.serverCodecConfigurer = serverCodecConfigurer;
	}

	/**
	 * 构建异常统一处理配置.
	 *
	 * @param messageSource         消息源
	 * @param stackTracesHandler    堆栈处理
	 * @param globalExceptionLogger 默认全局异常处理日志记录器
	 * @return 异常统一处理配置
	 */
	@Bean
	public GlobalExceptionHandler webFluxGlobalExceptionHandler(MessageSource messageSource,
			StackTracesHandler stackTracesHandler, GlobalExceptionLogger globalExceptionLogger,
			ThrowableResponseBuilder defaultBuilder) {
		Collection<ResponseBuilder> builders = ServiceLoader.load(ResponseBuilder.class).stream()
				.map(ServiceLoader.Provider::get).toList();
		return new GlobalExceptionHandler(messageSource, stackTracesHandler, globalExceptionLogger, builders, defaultBuilder);
	}

	/**
	 * 构建异常处理.
	 *
	 * @param errorAttributes           错误属性
	 * @param applicationContext        应用上下文
	 * @param globalExceptionHandler    全局错误处理
	 * @param globalExceptionProperties 全局异常处理配置
	 * @return 异常处理
	 */
	@Order(-2)
	@Bean
	public AbstractErrorWebExceptionHandler webFluxErrorWebExceptionHandler(ErrorAttributes errorAttributes,
			ApplicationContext applicationContext, GlobalExceptionHandler globalExceptionHandler, GlobalExceptionProperties globalExceptionProperties) {
		AbstractErrorWebExceptionHandler exceptionHandler = new WebFluxErrorWebExceptionHandler(errorAttributes,
				resourceProperties, this.serverProperties.getError(), applicationContext, globalExceptionHandler, globalExceptionProperties);
		exceptionHandler.setViewResolvers(this.viewResolvers);
		exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
		exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
		return exceptionHandler;
	}
}
