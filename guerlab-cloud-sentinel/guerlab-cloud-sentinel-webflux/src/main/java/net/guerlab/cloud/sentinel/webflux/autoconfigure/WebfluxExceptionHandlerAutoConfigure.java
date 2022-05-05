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

package net.guerlab.cloud.sentinel.webflux.autoconfigure;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.csp.sentinel.adapter.spring.webflux.SentinelWebFluxFilter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebExceptionHandler;

import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.web.core.autoconfigure.GlobalExceptionHandlerAutoConfigure;

/**
 * 自定义限流处理自动配置.
 *
 * @author guer
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@AutoConfigureAfter(GlobalExceptionHandlerAutoConfigure.class)
public class WebfluxExceptionHandlerAutoConfigure {

	private final List<ViewResolver> viewResolvers;

	private final ServerCodecConfigurer serverCodecConfigurer;

	/**
	 * 初始化自定义限流处理自动配置.
	 *
	 * @param viewResolvers         ViewResolver列表
	 * @param serverCodecConfigurer serverCodecConfigurer
	 */
	public WebfluxExceptionHandlerAutoConfigure(ObjectProvider<ViewResolver> viewResolvers,
			ServerCodecConfigurer serverCodecConfigurer) {
		this.viewResolvers = viewResolvers.stream().collect(Collectors.toList());
		this.serverCodecConfigurer = serverCodecConfigurer;
	}

	/**
	 * 创建sentinelBlockExceptionHandler.
	 *
	 * @param stackTracesHandler 堆栈处理
	 * @return sentinelBlockExceptionHandler
	 */
	@Bean
	@Order(-1)
	public WebExceptionHandler sentinelBlockExceptionHandler(StackTracesHandler stackTracesHandler) {
		return new SentinelBlockExceptionHandler(viewResolvers, serverCodecConfigurer,
				new CustomerBlockRequestHandler(stackTracesHandler));
	}

	/**
	 * 创建SentinelWebFluxFilter.
	 *
	 * @return SentinelWebFluxFilter
	 */
	@Bean
	@Order(-1)
	public SentinelWebFluxFilter sentinelWebFluxFilter() {
		return new SentinelWebFluxFilter();
	}
}
