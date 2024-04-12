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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import net.guerlab.commons.exception.ApplicationException;

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

		private final ObjectMapper objectMapper;

		/**
		 * 初始化响应数据处理.
		 *
		 * @param support      响应对象包装支持
		 * @param objectMapper objectMapper
		 */
		public ResponseAdvice(ResponseBodyWrapperSupport support, ObjectMapper objectMapper) {
			this.support = support;
			this.objectMapper = objectMapper;
		}

		@Override
		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
			return support.supports(returnType);
		}

		@Override
		public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
				Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
				ServerHttpResponse response) {

			if (support.noConvertObject(returnType)) {
				log.debug("un wrapper with noConvertObject");
				return body;
			}
			else if (matchExcluded(request, returnType.getMethod())) {
				log.debug("un wrapper with matchExcluded");
				return body;
			}

			boolean returnTypeIsString = Objects.equals(Objects.requireNonNull(returnType.getMethod())
					.getReturnType(), String.class);

			if (returnTypeIsString) {
				if (body == null) {
					return returnString(new Succeed<>(), response);
				}
				else {
					return returnString(body, response);
				}
			}
			else {
				if (body == null) {
					log.debug("wrapper with null body and not string type");
					return new Succeed<>();
				}
				else {
					log.debug("wrap up");
					return new Succeed<>(body);
				}
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

		private Object returnString(Object body, ServerHttpResponse response) {
			log.debug("wrapper with string type");
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

			Succeed<Object> result = new Succeed<>();
			result.setData(body);
			try {
				return objectMapper.writeValueAsString(result);
			}
			catch (JsonProcessingException e) {
				throw new ApplicationException(e);
			}
		}
	}
}
