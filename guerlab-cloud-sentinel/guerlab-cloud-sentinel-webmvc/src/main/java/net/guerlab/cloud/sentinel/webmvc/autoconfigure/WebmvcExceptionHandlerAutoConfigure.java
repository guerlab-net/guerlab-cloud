/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.sentinel.webmvc.autoconfigure;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 自定义限流处理自动配置.
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebmvcExceptionHandlerAutoConfigure {

	/**
	 * 构造自定义限流处理.
	 *
	 * @return 自定义限流处理
	 */
	@Bean
	public CustomerBlockExceptionHandler customerBlockExceptionHandler() {
		return new CustomerBlockExceptionHandler();
	}

	/**
	 * 自定义限流处理.
	 *
	 * @author guer
	 */
	public static class CustomerBlockExceptionHandler implements BlockExceptionHandler {

		private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) {
			if (HttpMethod.GET.matches(request.getMethod())) {
				String queryString = StringUtils.trimToNull(request.getQueryString());

				if (queryString != null) {
					request.getRequestURL().append("?").append(queryString);
				}
			}

			throw new ApplicationException(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName(),
					HttpStatus.TOO_MANY_REQUESTS.value());
		}
	}
}
