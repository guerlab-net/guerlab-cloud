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
import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;
import net.guerlab.web.result.Succeed;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * 响应数据处理自动配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@ConditionalOnClass(ResponseBodyAdvice.class)
@ConditionalOnProperty(prefix = "spring.web", name = "wrapper-response", havingValue = "true", matchIfMissing = true)
public class WebMvcResponseAdvisorAutoconfigure {

    /**
     * 响应数据处理
     *
     * @author guer
     */
    @SuppressWarnings("unused")
    @RestControllerAdvice
    public static class ResponseAdvice implements ResponseBodyAdvice<Object> {

        private final ResponseBodyWrapperSupport support;

        public ResponseAdvice(ResponseBodyWrapperSupport support) {
            this.support = support;
        }

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return support.notHasAnnotation(returnType);
        }

        @Override
        public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                ServerHttpResponse response) {
            if (support.noConvertObject(body) || matchExcluded(request, returnType.getMethod())) {
                return body;
            }
            return new Succeed<>(body);
        }

        private boolean matchExcluded(ServerHttpRequest request, @Nullable Method method) {
            return support.matchExcluded(getRequestPath(request), method);
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
