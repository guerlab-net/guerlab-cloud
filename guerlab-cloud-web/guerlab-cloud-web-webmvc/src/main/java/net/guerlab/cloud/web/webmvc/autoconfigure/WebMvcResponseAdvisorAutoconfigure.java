/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.web.webmvc.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.annotation.IgnoreResponseHandler;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.web.result.Result;
import net.guerlab.web.result.Succeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 响应数据处理自动配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@ConditionalOnClass(ResponseBodyAdvice.class)
@ConditionalOnProperty(prefix = "spring.web", name = "wrapper-response", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ResponseAdvisorProperties.class)
public class WebMvcResponseAdvisorAutoconfigure {

    /**
     * 响应数据处理
     *
     * @author guer
     */
    @RestControllerAdvice
    public static class ResponseAdvice implements ResponseBodyAdvice<Object> {

        private static final Class<?>[] NO_CONVERT_CLASS = new Class<?>[] { String.class, Result.class, byte[].class,
                InputStream.class, ResponseEntity.class };

        private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

        private ResponseAdvisorProperties properties;

        /**
         * 判断响应数据是否为不需要转换对象
         *
         * @param body
         *         响应数据
         * @return 是否需要转换
         */
        private static boolean noConvertObject(@Nullable Object body) {
            return Arrays.stream(NO_CONVERT_CLASS).anyMatch(clazz -> clazz.isInstance(body));
        }

        /**
         * 判断方法或者类上是否包含指定注解
         *
         * @param returnType
         *         方法参数对象
         * @param annotationClass
         *         注解类
         * @return 是否包含注解
         */
        @SuppressWarnings("SameParameterValue")
        private static boolean hasAnnotation(MethodParameter returnType, Class<? extends Annotation> annotationClass) {
            Method method = returnType.getMethod();
            if (method != null && AnnotationUtils.findAnnotation(method, annotationClass) != null) {
                return true;
            }

            Class<?> containingClass = returnType.getContainingClass();
            if (AnnotationUtils.findAnnotation(containingClass, annotationClass) != null) {
                return true;
            }

            return containingClass.getPackage().getDeclaredAnnotation(annotationClass) != null;
        }

        @Autowired
        public void setProperties(ResponseAdvisorProperties properties) {
            this.properties = properties;
        }

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return !hasAnnotation(returnType, IgnoreResponseHandler.class);
        }

        @Override
        public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                ServerHttpResponse response) {
            if (noConvertObject(body) || matchExcluded(request, returnType.getMethod())) {
                return body;
            }
            return new Succeed<>(body);
        }

        /**
         * 判断是否在排除路径中
         *
         * @param request
         *         请求对象
         * @param method
         *         处理方法
         * @return 是否在排除路径中
         */
        private boolean matchExcluded(ServerHttpRequest request, @Nullable Method method) {
            List<String> excluded = properties.getExcluded();
            if (method == null || CollectionUtil.isEmpty(excluded)) {
                return false;
            }

            String requestPath = getRequestPath(request);
            String methodName = method.getDeclaringClass().getName() + "#" + method.getName();

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

        private String getRequestPath(ServerHttpRequest request) {
            String requestPath = request.getURI().getPath();

            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
                String contextPath = servletServerHttpRequest.getServletRequest().getContextPath();

                String newRequestPath = requestPath.replaceFirst(contextPath, "");
                log.debug("replace requestPath[form={}, to={}]", requestPath, newRequestPath);
                requestPath = newRequestPath;
            }

            return requestPath;
        }
    }
}
