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

package net.guerlab.cloud.api.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.core.result.Result;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * 动态代理快速失败
 *
 * @author guer
 */
public class DynamicProxyFallback {

    @SuppressWarnings("unchecked")
    public static <T> T proxy(Class<T> targetClass, Throwable cause) {
        return (T) Proxy.newProxyInstance(DynamicProxyFallback.class.getClassLoader(), new Class[] { targetClass },
                new FallbackInvocationHandler(cause));
    }

    @Slf4j
    @AllArgsConstructor
    private static class FallbackInvocationHandler implements InvocationHandler {

        private final Throwable cause;

        @Nullable
        @SuppressWarnings("SuspiciousInvocationHandlerImplementation")
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            log.debug(String.format("%s fall", method.getName()), cause);

            Class<?> returnType = method.getReturnType();

            log.debug("returnType: {}", returnType);

            if (List.class.isAssignableFrom(returnType)) {
                return Collections.emptyList();
            } else if (Set.class.isAssignableFrom(returnType)) {
                return Collections.emptySet();
            } else if (Collection.class.isAssignableFrom(returnType)) {
                return Collections.emptyList();
            } else if (Map.class.isAssignableFrom(returnType)) {
                return Collections.emptyMap();
            } else if (Boolean.class.isAssignableFrom(returnType) || Boolean.TYPE.isAssignableFrom(returnType)) {
                return false;
            } else if (Result.class.isAssignableFrom(returnType)) {
                return new Fail<>();
            } else if (Pageable.class.isAssignableFrom(returnType)) {
                return Pageable.empty();
            } else if (Integer.TYPE.isAssignableFrom(returnType)) {
                return 0;
            } else if (Long.TYPE.isAssignableFrom(returnType)) {
                return 0L;
            }

            return null;
        }
    }
}
