package net.guerlab.smart.platform.commons.util;

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
