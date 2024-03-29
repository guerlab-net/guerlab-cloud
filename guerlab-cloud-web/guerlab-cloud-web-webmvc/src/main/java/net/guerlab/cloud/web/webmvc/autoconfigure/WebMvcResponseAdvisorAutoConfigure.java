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

package net.guerlab.cloud.web.webmvc.autoconfigure;

import java.lang.reflect.Method;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import net.guerlab.cloud.core.result.Succeed;
import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;

/**
 * 响应数据处理自动配置.
 *
 * @author guer
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(ResponseBodyAdvice.class)
@ConditionalOnProperty(prefix = "spring.web", name = "wrapper-response", havingValue = "true", matchIfMissing = true)
public class WebMvcResponseAdvisorAutoConfigure {

	/**
	 * 响应数据处理.
	 *
	 * @author guer
	 */
	@SuppressWarnings("unused")
	@RestControllerAdvice
	public static class ResponseAdvice implements ResponseBodyAdvice<Object> {

		private final ResponseBodyWrapperSupport support;

		/**
		 * 初始化响应数据处理.
		 *
		 * @param support 响应对象包装支持
		 */
		public ResponseAdvice(ResponseBodyWrapperSupport support) {
			this.support = support;
		}

		@Override
		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
			return support.supports(returnType);
		}

		@Override
		public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
				Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
				ServerHttpResponse response) {
			if (body == null) {
				if (Objects.equals(Objects.requireNonNull(returnType.getMethod()).getReturnType(), String.class)) {
					log.debug("wrapper with string type");
					return new Succeed<>().toString();
				}
				log.debug("wrapper with null body and not string type");
				return new Succeed<>();
			}
			else if (support.noConvertObject(body, returnType)) {
				log.debug("un wrapper with noConvertObject, body class is {}", body.getClass());
				return body;
			}
			else if (matchExcluded(request, returnType.getMethod())) {
				log.debug("un wrapper with matchExcluded");
				return body;
			}
			else {
				log.debug("wrap up");
				return new Succeed<>(body);
			}
		}

		private boolean matchExcluded(ServerHttpRequest request, @Nullable Method method) {
			return support.matchExcluded(getRequestPath(request), method);
		}

		private String getRequestPath(ServerHttpRequest request) {
			String requestPath = request.getURI().getPath();

			if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
				String contextPath = servletServerHttpRequest.getServletRequest().getContextPath();

				String newRequestPath = requestPath.replaceFirst(contextPath, "");
				log.debug("replace requestPath[form={}, to={}]", requestPath, newRequestPath);
				requestPath = newRequestPath;
			}

			return requestPath;
		}
	}
}
