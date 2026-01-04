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

package net.guerlab.cloud.gateway.core.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

/**
 * 错误处理自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
public class ErrorWebExceptionHandlerAutoConfiguration {

	private final ServerProperties serverProperties;

	private final ApplicationContext applicationContext;

	private final WebProperties.Resources resourceProperties;

	private final List<ViewResolver> viewResolvers;

	private final ServerCodecConfigurer serverCodecConfigurer;

	/**
	 * 创建实例.
	 *
	 * @param serverProperties      ServerProperties
	 * @param webProperties         WebProperties
	 * @param viewResolversProvider ViewResolver's ObjectProvider
	 * @param serverCodecConfigurer ServerCodecConfigurer
	 * @param applicationContext    ApplicationContext
	 */
	public ErrorWebExceptionHandlerAutoConfiguration(ServerProperties serverProperties, WebProperties webProperties,
			ObjectProvider<ViewResolver> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer,
			ApplicationContext applicationContext) {
		this.serverProperties = serverProperties;
		this.applicationContext = applicationContext;
		this.resourceProperties = webProperties.getResources();
		this.viewResolvers = viewResolversProvider.orderedStream().toList();
		this.serverCodecConfigurer = serverCodecConfigurer;
	}

	/**
	 * 构造响应状态异常信息处理.
	 *
	 * @return 响应状态异常信息处理
	 */
	@Bean
	public ExceptionMessageHandler responseStatusExceptionMessageHandler() {
		return new ResponseStatusExceptionMessageHandler();
	}

	/**
	 * 构造自定义错误处理.
	 *
	 * @param errorAttributes  errorAttributes
	 * @param handlersProvider 异常信息处理对象列表
	 * @param wrappersProvider 错误信息包装处理器列表
	 * @return 自定义错误处理
	 */
	@Bean
	@ConditionalOnMissingBean(value = ErrorWebExceptionHandler.class, search = SearchStrategy.CURRENT)
	@Order(-1)
	public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes,
			ObjectProvider<ExceptionMessageHandler> handlersProvider,
			ObjectProvider<ErrorAttributesWrapper> wrappersProvider) {
		List<ExceptionMessageHandler> handlers = getExceptionMessageHandlers(handlersProvider);

		log.debug("use exceptionMessageHandlers: {}", handlers);

		CustomErrorWebExceptionHandler exceptionHandler = new CustomErrorWebExceptionHandler(errorAttributes,
				resourceProperties, this.serverProperties.getError(), applicationContext, handlers, wrappersProvider);
		exceptionHandler.setViewResolvers(this.viewResolvers);
		exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
		exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
		return exceptionHandler;
	}

	private List<ExceptionMessageHandler> getExceptionMessageHandlers(
			ObjectProvider<ExceptionMessageHandler> handlersProvider) {
		List<ExceptionMessageHandler> handlers = new ArrayList<>();
		handlersProvider.stream().forEach(handlers::add);
		ServiceLoader.load(ExceptionMessageHandler.class).forEach(handlers::add);
		return handlers;
	}
}
