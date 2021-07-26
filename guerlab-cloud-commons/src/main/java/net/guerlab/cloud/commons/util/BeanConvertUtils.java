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

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Bean转换工具类
 *
 * @author guer
 */
@SuppressWarnings({ "unused" })
public class BeanConvertUtils {

    private BeanConvertUtils() {
    }

    /**
     * 转换为目标类型
     *
     * @param <T>
     *         目标类型
     * @param <E>
     *         实体类型
     * @param entity
     *         实体
     * @return 目标类型
     */
    @Nullable
    public static <T, E extends Convert<T>> T toObject(@Nullable E entity) {
        return entity == null ? null : entity.convert();
    }

    /**
     * 转换为目标类型
     *
     * @param <T>
     *         目标类型
     * @param <E>
     *         实体类型
     * @param entity
     *         实体
     * @param targetClass
     *         目标类型
     * @return 目标类型
     */
    @Nullable
    public static <T, E> T toObject(@Nullable E entity, Class<T> targetClass) {
        if (entity == null) {
            return null;
        }

        T target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        BeanUtils.copyProperties(entity, target);

        return target;
    }

    /**
     * 转换为目标列表
     *
     * @param <T>
     *         目标类型
     * @param <E>
     *         实体类型
     * @param entityList
     *         实体列表
     * @return 目标列表
     */
    public static <T, E extends Convert<T>> List<T> toList(Collection<E> entityList) {
        return CollectionUtil.toList(entityList, Convert::convert);
    }

    /**
     * 转换为目标列表
     *
     * @param <T>
     *         目标类型
     * @param <E>
     *         实体类型
     * @param entityList
     *         实体列表
     * @param targetClass
     *         目标类型
     * @return 目标列表
     */
    public static <T, E> List<T> toList(Collection<E> entityList, Class<T> targetClass) {
        return CollectionUtil.toList(entityList, e -> toObject(e, targetClass));
    }

    /**
     * 转换为目标列表对象
     *
     * @param <T>
     *         目标类型
     * @param <E>
     *         实体类型
     * @param list
     *         实体列表对象
     * @return 目标列表对象
     */
    public static <T, E extends Convert<T>> ListObject<T> toListObject(@Nullable ListObject<E> list) {
        if (list == null || CollectionUtil.isEmpty(list.getList())) {
            return ListObject.empty();
        }

        ListObject<T> result = copyListObject(list);

        result.setList(toList(list.getList()));

        return result;
    }

    /**
     * 转换为目标列表对象
     *
     * @param <T>
     *         目标类型
     * @param <E>
     *         实体类型
     * @param list
     *         实体列表对象
     * @param dtoClass
     *         目标类型
     * @return 目标列表对象
     */
    public static <T, E> ListObject<T> toListObject(@Nullable ListObject<E> list, Class<T> dtoClass) {
        if (list == null || CollectionUtil.isEmpty(list.getList())) {
            return ListObject.empty();
        }

        ListObject<T> result = copyListObject(list);

        result.setList(toList(list.getList(), dtoClass));

        return result;
    }

    private static <T> ListObject<T> copyListObject(ListObject<?> origin) {
        ListObject<T> result = new ListObject<>();
        result.setPageSize(origin.getPageSize());
        result.setCount(origin.getCount());
        result.setCurrentPageId(origin.getCurrentPageId());
        result.setMaxPageId(origin.getMaxPageId());

        return result;
    }

}
