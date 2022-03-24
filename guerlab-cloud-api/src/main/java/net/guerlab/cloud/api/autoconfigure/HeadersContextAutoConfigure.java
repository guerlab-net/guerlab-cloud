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

package net.guerlab.cloud.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import net.guerlab.cloud.api.headers.HeadersContextCleanHandlerInterceptor;
import net.guerlab.cloud.api.headers.HeadersRequestInterceptor;
import net.guerlab.cloud.loadbalancer.autoconfigure.GlobalLoadBalancerAutoConfiguration;

/**
 * 请求头处理相关自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
@AutoConfigureAfter(GlobalLoadBalancerAutoConfiguration.class)
public class HeadersContextAutoConfigure {

	/**
	 * 构建请求头处理请求拦截器.
	 *
	 * @return 请求头处理请求拦截器
	 */
	@Bean
	public HeadersRequestInterceptor headersRequestInterceptor() {
		return new HeadersRequestInterceptor();
	}

	/**
	 * 构造请求信息清理拦截器.
	 *
	 * @return 请求信息清理拦截器
	 */
	@Bean
	@Conditional(HeadersContextAutoConfigure.WrapperCondition.class)
	public HeadersContextCleanHandlerInterceptor headersContextCleanHandlerInterceptor() {
		return new HeadersContextCleanHandlerInterceptor();
	}

	static class WrapperCondition implements Condition {

		@Override
		public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
			try {
				return WrapperCondition.class.getClassLoader()
						.loadClass("org.springframework.web.servlet.HandlerInterceptor") != null;
			}
			catch (Exception e) {
				return false;
			}
		}
	}
}
