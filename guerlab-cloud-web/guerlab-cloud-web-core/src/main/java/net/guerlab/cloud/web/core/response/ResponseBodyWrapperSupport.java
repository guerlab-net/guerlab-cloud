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
package net.guerlab.cloud.web.core.response;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.annotation.IgnoreResponseHandler;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.web.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 响应对象包装支持
 *
 * @author guer
 */
@Slf4j
public class ResponseBodyWrapperSupport {

    private static final Class<?>[] NO_CONVERT_CLASS = new Class<?>[] { String.class, Result.class, byte[].class,
            InputStream.class, ResponseEntity.class };

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    private final ResponseAdvisorProperties properties;

    public ResponseBodyWrapperSupport(ResponseAdvisorProperties properties) {
        this.properties = properties;
    }

    /**
     * 判断响应数据是否为不需要转换对象
     *
     * @param body
     *         响应数据
     * @return 是否需要转换
     */
    public boolean noConvertObject(@Nullable Object body) {
        return Arrays.stream(NO_CONVERT_CLASS).anyMatch(clazz -> clazz.isInstance(body));
    }

    /**
     * 判断方法或者类上是否包含IgnoreResponseHandler注解
     *
     * @param returnType
     *         方法参数对象
     * @return 是否不包含注解
     */
    @SuppressWarnings("SameParameterValue")
    public boolean notHasAnnotation(MethodParameter returnType) {
        Method method = returnType.getMethod();
        if (method != null && AnnotationUtils.findAnnotation(method, IgnoreResponseHandler.class) != null) {
            return false;
        }

        Class<?> containingClass = returnType.getContainingClass();
        if (AnnotationUtils.findAnnotation(containingClass, IgnoreResponseHandler.class) != null) {
            return false;
        }

        return containingClass.getPackage().getDeclaredAnnotation(IgnoreResponseHandler.class) == null;
    }

    /**
     * 判断是否在排除路径中
     *
     * @param requestPath
     *         请求路径
     * @param method
     *         处理方法
     * @return 是否在排除路径中
     */
    public boolean matchExcluded(String requestPath, @Nullable Method method) {
        List<String> excluded = properties.getExcluded();
        if (method == null || CollectionUtil.isEmpty(excluded)) {
            return false;
        }

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
}
