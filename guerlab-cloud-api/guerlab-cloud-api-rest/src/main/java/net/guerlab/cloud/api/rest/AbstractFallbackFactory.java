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

import lombok.extern.slf4j.Slf4j;
import net.guerlab.commons.exception.ApplicationException;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象快速失败类构建工厂
 *
 * @author guer
 */
@Slf4j
public abstract class AbstractFallbackFactory<T> implements FallbackFactory<T> {

    private static final Map<Class<?>, Class<?>> CACHE = new ConcurrentHashMap<>();

    @Override
    public T create(Throwable cause) {
        return DynamicProxyFallback.proxy(getProxyClass(), cause);
    }

    @SuppressWarnings({ "unchecked" })
    private Class<T> getProxyClass() {
        Class<?> refClass = this.getClass();
        Class<T> result = (Class<T>) CACHE.get(refClass);

        if (result != null) {
            return result;
        }

        log.debug("parse result class by ref: {}", refClass);
        Class<?> currentClass = refClass;
        Class<?> supperClass;
        while (!Object.class.equals(currentClass)) {
            supperClass = currentClass.getSuperclass();
            if (!AbstractFallbackFactory.class.equals(supperClass)) {
                currentClass = supperClass;
                continue;
            }

            Type type = currentClass.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            result = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            CACHE.put(refClass, result);
            return result;
        }
        throw new ApplicationException("fallback instance invalid");
    }
}
