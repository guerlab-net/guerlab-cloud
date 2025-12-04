/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import jakarta.annotation.Nullable;

import org.springframework.beans.BeanUtils;

import net.guerlab.cloud.core.dto.Convert;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;

/**
 * Bean转换工具类.
 *
 * @author guer
 */
@SuppressWarnings({"unused"})
public final class BeanConvertUtils {

	private BeanConvertUtils() {
	}

	/**
	 * 转换为目标类型.
	 *
	 * @param <T>    目标类型
	 * @param <E>    实体类型
	 * @param entity 实体
	 * @return 目标类型
	 */
	@Nullable
	public static <T, E extends Convert<T>> T toObject(@Nullable E entity) {
		return entity == null ? null : entity.convert();
	}

	/**
	 * 转换为目标类型.
	 *
	 * @param <T>         目标类型
	 * @param <E>         实体类型
	 * @param entity      实体
	 * @param targetClass 目标类型
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
		}
		catch (Exception e) {
			throw new ApplicationException(e.getLocalizedMessage(), e);
		}

		BeanUtils.copyProperties(entity, target);

		return target;
	}

	/**
	 * 转换为目标列表.
	 *
	 * @param <T>        目标类型
	 * @param <E>        实体类型
	 * @param entityList 实体列表
	 * @return 目标列表
	 */
	public static <T, E extends Convert<T>> List<T> toList(Collection<E> entityList) {
		return CollectionUtil.toList(entityList, Convert::convert);
	}

	/**
	 * 转换为目标列表.
	 *
	 * @param <T>         目标类型
	 * @param <E>         实体类型
	 * @param entityList  实体列表
	 * @param targetClass 目标类型
	 * @return 目标列表
	 */
	public static <T, E> List<T> toList(Collection<E> entityList, Class<T> targetClass) {
		return CollectionUtil.toList(entityList, e -> toObject(e, targetClass));
	}

	/**
	 * 转换为目标列表对象.
	 *
	 * @param <T>  目标类型
	 * @param <E>  实体类型
	 * @param list 实体列表对象
	 * @return 目标列表对象
	 */
	public static <T, E extends Convert<T>> Pageable<T> toPageable(@Nullable Pageable<E> list) {
		if (list == null) {
			return Pageable.empty();
		}

		Pageable<T> result = copyPageable(list);

		result.setList(toList(list.getList()));

		return result;
	}

	/**
	 * 转换为目标列表对象.
	 *
	 * @param <T>      目标类型
	 * @param <E>      实体类型
	 * @param list     实体列表对象
	 * @param dtoClass 目标类型
	 * @return 目标列表对象
	 */
	public static <T, E> Pageable<T> toPageable(@Nullable Pageable<E> list, Class<T> dtoClass) {
		if (list == null) {
			return Pageable.empty();
		}

		Pageable<T> result = copyPageable(list);

		result.setList(toList(list.getList(), dtoClass));

		return result;
	}

	/**
	 * 转换为目标列表对象.
	 *
	 * @param <T>             目标类型
	 * @param <E>             实体类型
	 * @param list            实体列表对象
	 * @param convertFunction 对象转换方法
	 * @return 目标列表对象
	 */
	public static <T, E> Pageable<T> toPageable(@Nullable Pageable<E> list, Function<E, T> convertFunction) {
		if (list == null) {
			return Pageable.empty();
		}

		Pageable<T> result = copyPageable(list);

		result.setList(CollectionUtil.toList(list.getList(), convertFunction));

		return result;
	}

	private static <T> Pageable<T> copyPageable(Pageable<?> origin) {
		Pageable<T> result = new Pageable<>();
		result.setPageSize(origin.getPageSize());
		result.setCount(origin.getCount());
		result.setCurrentPageId(origin.getCurrentPageId());
		result.setMaxPageId(origin.getMaxPageId());

		return result;
	}

}
