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
package net.guerlab.cloud.commons.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 范型工具类
 *
 * @author guer
 */
public class ParameterizedTypeUtils {

    private ParameterizedTypeUtils() {

    }

    /**
     * 获取范型类型
     *
     * @param obj
     *         对象
     * @param index
     *         所要获取的范型的ID
     * @return 范型类型
     */
    private static Type getType(Object obj, int index) {
        if (obj == null) {
            throw new NullPointerException("object is null");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index must >= 0");
        }

        Type type = obj.getClass().getGenericSuperclass();

        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        ParameterizedType parameterized = (ParameterizedType) type;

        Type[] actualTypeArguments = parameterized.getActualTypeArguments();

        return actualTypeArguments[index];
    }

    /**
     * 获取范型类
     *
     * @param obj
     *         对象
     * @param index
     *         所要获取的范型的ID
     * @param <T>
     *         范型类
     * @return 范型类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getByClass(Object obj, int index) {
        return (Class<T>) getType(obj, index);
    }

}
