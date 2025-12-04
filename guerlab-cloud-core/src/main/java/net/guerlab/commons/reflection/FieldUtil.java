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

package net.guerlab.commons.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类字段工具.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class FieldUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

	private static final Class<?>[] BASE_NUMBER_CLASS_LIST = new Class<?>[] {Byte.TYPE, Short.TYPE, Integer.TYPE,
			Long.TYPE, Float.TYPE, Double.TYPE};

	private static final Map<Class<?>, List<Field>> CACHE = new ConcurrentHashMap<>();

	private FieldUtil() {
	}

	/**
	 * 读取属性值.
	 *
	 * @param object 对象
	 * @param field  字段
	 * @return 属性值
	 */
	@Nullable
	public static Object get(Object object, @Nullable Field field) {
		if (field == null) {
			return null;
		}
		try {
			return read(object, field);
		}
		catch (Exception e) {
			LOGGER.trace(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 读取属性值.
	 *
	 * @param object 对象
	 * @param name   属性名
	 * @return 属性值
	 */
	@Nullable
	public static Object get(Object object, String name) {
		return get(object, getField(object.getClass(), name));
	}

	/**
	 * 读取属性值.
	 *
	 * @param object 对象
	 * @param name   属性名
	 * @return 属性值
	 * @throws IllegalAccessException    if this {@code Method} object
	 *                                   is enforcing Java language access control and the underlying
	 *                                   method is inaccessible.
	 * @throws InvocationTargetException if the underlying method
	 *                                   throws an exception.
	 * @throws IntrospectionException    if an exception occurs during introspection.
	 */
	@Nullable
	public static Object read(Object object, String name)
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		return read(object, getField(object.getClass(), name));
	}

	/**
	 * 读取属性值.
	 *
	 * @param object 对象
	 * @param field  字段
	 * @return 属性值
	 * @throws IllegalAccessException    if this {@code Method} object
	 *                                   is enforcing Java language access control and the underlying
	 *                                   method is inaccessible.
	 * @throws InvocationTargetException if the underlying method
	 *                                   throws an exception.
	 * @throws IntrospectionException    if an exception occurs during introspection.
	 */
	@Nullable
	public static Object read(Object object, @Nullable Field field)
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		if (field == null) {
			return null;
		}
		else if (Modifier.isPublic(field.getModifiers())) {
			return field.get(object);
		}

		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
		Method method = propertyDescriptor.getReadMethod();
		return method.invoke(object);
	}

	/**
	 * 设置属性.
	 *
	 * @param object 对象
	 * @param field  字段
	 * @param value  属性值
	 */
	public static void put(Object object, @Nullable Field field, @Nullable Object value) {
		if (field == null) {
			return;
		}
		try {
			write(object, field, value);
		}
		catch (Exception e) {
			LOGGER.trace(e.getMessage(), e);
		}
	}

	/**
	 * 设置属性.
	 *
	 * @param object 对象
	 * @param name   属性名
	 * @param value  属性值
	 */
	public static void put(Object object, String name, @Nullable Object value) {
		put(object, getField(object.getClass(), name), value);
	}

	/**
	 * 设置属性.
	 *
	 * @param object 对象
	 * @param name   属性名
	 * @param value  属性值
	 * @throws IntrospectionException    if an exception occurs during
	 *                                   introspection.
	 * @throws InvocationTargetException if the underlying method
	 *                                   throws an exception.
	 * @throws IllegalAccessException    if this {@code Method} object
	 *                                   is enforcing Java language access control and the underlying
	 *                                   method is inaccessible.
	 */
	public static void write(Object object, String name, @Nullable Object value)
			throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		write(object, getField(object.getClass(), name), value);
	}

	/**
	 * 设置属性.
	 *
	 * @param object 对象
	 * @param field  字段
	 * @param value  属性值
	 * @throws IntrospectionException    if an exception occurs during
	 *                                   introspection.
	 * @throws InvocationTargetException if the underlying method
	 *                                   throws an exception.
	 * @throws IllegalAccessException    if this {@code Method} object
	 *                                   is enforcing Java language access control and the underlying
	 *                                   method is inaccessible.
	 */
	public static void write(Object object, @Nullable Field field, @Nullable Object value)
			throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		if (field == null) {
			return;
		}
		else if (Modifier.isPublic(field.getModifiers())) {
			field.set(object, value);
			return;
		}

		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
		Method method = propertyDescriptor.getWriteMethod();
		method.invoke(object, value);
	}

	/**
	 * 判断某个类型是否为数值类.
	 *
	 * @param fieldType 字段类型
	 * @return 是否为数值类型
	 */
	public static boolean isNumberClass(@Nullable Class<?> fieldType) {
		if (fieldType == null) {
			return false;
		}

		if (Number.class.isAssignableFrom(fieldType)) {
			return true;
		}

		return Arrays.stream(BASE_NUMBER_CLASS_LIST).anyMatch(element -> element == fieldType);
	}

	/**
	 * 根据类对象获取字段列表.
	 *
	 * @param clazz 类对象
	 * @return 字段列表
	 */
	public static List<Field> getFields(@Nullable Class<?> clazz) {
		if (clazz == null) {
			return Collections.emptyList();
		}

		List<Field> allFields = CACHE.get(clazz);

		if (allFields != null) {
			return allFields;
		}

		allFields = new ArrayList<>();
		Deque<Class<?>> classes = new LinkedList<>();
		Class<?> paramsClass = clazz;

		while (true) {
			Class<?> superClass = paramsClass.getSuperclass();
			classes.add(paramsClass);

			if (Object.class.equals(superClass)) {
				break;
			}

			paramsClass = paramsClass.getSuperclass();
		}

		while (!classes.isEmpty()) {
			allFields.addAll(Arrays.asList(classes.pollFirst().getDeclaredFields()));
		}

		CACHE.put(clazz, allFields);

		return allFields;
	}

	/**
	 * 根据类对象获取字段列表.
	 *
	 * @param clazz 类对象
	 * @return 字段列表
	 */
	@Nullable
	public static Field getField(Class<?> clazz, String name) {
		return getFields(clazz).stream().filter(field -> Objects.equals(field.getName(), name)).findFirst()
				.orElse(null);
	}

	/**
	 * 获取过滤后的字段列表.
	 *
	 * @param clazz   类对象
	 * @param filters 过滤器列表
	 * @return 字段列表
	 */
	public static List<Field> getFieldsWithFilter(Class<?> clazz, @Nullable Collection<Predicate<Field>> filters) {
		List<Field> list = getFields(clazz);

		if (list.isEmpty()) {
			return Collections.emptyList();
		}

		Stream<Field> stream = list.stream().filter(Objects::nonNull);

		if (filters != null && !filters.isEmpty()) {
			for (Predicate<Field> filter : filters) {
				if (filter != null) {
					stream = stream.filter(filter);
				}
			}
		}

		return stream.collect(Collectors.toList());
	}

	/**
	 * 获取过滤后的字段列表.
	 *
	 * @param clazz   类对象
	 * @param filters 过滤器列表
	 * @return 字段列表
	 */
	@SafeVarargs
	public static List<Field> getFieldsWithFilter(Class<?> clazz, Predicate<Field>... filters) {
		return getFieldsWithFilter(clazz, Arrays.asList(filters));
	}
}
