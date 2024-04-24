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

package net.guerlab.cloud.web.core.response;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;

import net.guerlab.cloud.core.result.Result;
import net.guerlab.cloud.web.core.annotation.ResponseObjectWrapper;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 响应对象包装支持.
 *
 * @author guer
 */
@Slf4j
public class ResponseBodyWrapperSupport {

	private static final Class<?>[] NO_CONVERT_CLASS = new Class<?>[] {CharSequence.class, Result.class, byte[].class,
			InputStream.class, ResponseEntity.class};

	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	private final ResponseAdvisorProperties properties;

	/**
	 * 初始化响应对象包装支持.
	 *
	 * @param properties http响应数据处理配置参数
	 */
	public ResponseBodyWrapperSupport(ResponseAdvisorProperties properties) {
		this.properties = properties;
	}

	/**
	 * 判断响应是否为不需要转换对象.
	 *
	 * @param bodyTypeParameter 方法参数对象
	 * @return 是否需要转换
	 */
	public boolean noConvertObject(MethodParameter bodyTypeParameter) {
		ResponseObjectWrapper responseHandlerWrapper = getMethodAnnotation(bodyTypeParameter);
		if (responseHandlerWrapper != null && responseHandlerWrapper.force()) {
			return false;
		}

		Class<?> returnTypeClass = Objects.requireNonNull(bodyTypeParameter.getMethod()).getReturnType();

		return Arrays.stream(NO_CONVERT_CLASS).anyMatch(clazz -> clazz.isAssignableFrom(returnTypeClass));
	}

	/**
	 * 判断只是支持结果包装.
	 *
	 * @param returnType 方法参数对象
	 * @return 是否支持结果包装
	 */
	public boolean supports(MethodParameter returnType) {
		ResponseObjectWrapper responseHandlerWrapper = getAnnotation(returnType);
		return responseHandlerWrapper == null || !responseHandlerWrapper.ignore();
	}

	@Nullable
	private ResponseObjectWrapper getAnnotation(MethodParameter returnType) {
		ResponseObjectWrapper responseHandlerWrapper = getMethodAnnotation(returnType);
		if (responseHandlerWrapper != null) {
			return responseHandlerWrapper;
		}

		Class<?> containingClass = returnType.getContainingClass();
		responseHandlerWrapper = AnnotationUtils.findAnnotation(containingClass, ResponseObjectWrapper.class);
		if (responseHandlerWrapper == null) {
			responseHandlerWrapper = containingClass.getPackage().getDeclaredAnnotation(ResponseObjectWrapper.class);
		}
		return responseHandlerWrapper;
	}

	@Nullable
	private ResponseObjectWrapper getMethodAnnotation(MethodParameter returnType) {
		Method method = returnType.getMethod();
		if (method != null) {
			return AnnotationUtils.findAnnotation(method, ResponseObjectWrapper.class);
		}
		return null;
	}

	/**
	 * 判断是否在排除路径中.
	 *
	 * @param requestPath 请求路径
	 * @param method      处理方法
	 * @return 是否在排除路径中
	 */
	public boolean matchExcluded(String requestPath, @Nullable Method method) {
		List<String> excluded = properties.getExcluded();
		if (method == null || CollectionUtil.isEmpty(excluded)) {
			return false;
		}

		String methodName = method.getDeclaringClass().getName() + "#" + method.getName();
		log.debug("method sign {}", methodName);

		for (String pattern : excluded) {
			if (ANT_PATH_MATCHER.match(pattern, requestPath)) {
				log.debug("matchExcluded: [use ant path: {}, requestPath: {}]", pattern, requestPath);
				return true;
			}
			if (methodName.equals(pattern)) {
				log.debug("matchExcluded: [use class path: {}, requestPath: {}]", pattern, requestPath);
				return true;
			}
		}

		log.debug("matchExcluded: [unMatch, requestPath: {}]", requestPath);
		return false;
	}
}
