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

package net.guerlab.cloud.searchparams;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import org.springframework.lang.Nullable;

import net.guerlab.commons.reflection.FieldUtil;

/**
 * SearchParams工具类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class SearchParamsUtils {

	private static final Predicate<Field> STATIC_FILTER = e -> e != null && !Modifier.isStatic(e.getModifiers());

	private static final Predicate<Field> PAGE_PARAMS_FILTER = e -> !SearchParams.class.getName()
			.equals(e.getDeclaringClass().getName());

	private static final HashMap<Class<? extends AbstractSearchParamsUtilInstance>, AbstractSearchParamsUtilInstance> INSTANCES_CACHE = new HashMap<>();

	private static final HashMap<Class<? extends SqlProvider>, SqlProvider> SQL_PROVIDER_CACHE = new HashMap<>();

	static {
		ServiceLoader.load(AbstractSearchParamsUtilInstance.class)
				.forEach((instance) -> INSTANCES_CACHE.put(instance.getClass(), instance));
	}

	private SearchParamsUtils() {
	}

	/**
	 * 注册实例.
	 *
	 * @param instance 实例
	 */
	public static void register(AbstractSearchParamsUtilInstance instance) {
		INSTANCES_CACHE.put(instance.getClass(), instance);
	}

	/**
	 * 注销实例.
	 *
	 * @param instance 实例
	 */
	public static void unRegister(AbstractSearchParamsUtilInstance instance) {
		INSTANCES_CACHE.remove(instance.getClass());
	}

	/**
	 * 注销指定类实例.
	 *
	 * @param type 类
	 */
	public static void unRegister(Class<? extends AbstractSearchParamsUtilInstance> type) {
		INSTANCES_CACHE.remove(type);
	}

	/**
	 * 获取指定类实例.
	 *
	 * @param type 类
	 * @return 实例
	 */
	public static AbstractSearchParamsUtilInstance getInstance(Class<? extends AbstractSearchParamsUtilInstance> type) {
		return INSTANCES_CACHE.get(type);
	}

	/**
	 * 获取实例列表.
	 *
	 * @return 实例列表
	 */
	public static Collection<AbstractSearchParamsUtilInstance> getInstances() {
		return Collections.unmodifiableCollection(INSTANCES_CACHE.values());
	}

	/**
	 * 对searchParams进行处理.
	 *
	 * @param searchParams 参数列表对象
	 * @param object       输出对象
	 */
	public static void handler(SearchParams searchParams, Object object) {
		INSTANCES_CACHE.values().stream().filter(instance -> instance.accept(object)).findFirst()
				.ifPresent(searchParamsUtilInstance -> handler(searchParams, object, searchParamsUtilInstance));
	}

	/**
	 * 以指定实例对searchParams进行处理.
	 *
	 * @param searchParams 参数列表对象
	 * @param object       输出对象
	 * @param instance     处理实例
	 */
	public static void handler(SearchParams searchParams, Object object, AbstractSearchParamsUtilInstance instance) {
		getFields(searchParams).forEach(field -> setValue(field, object, searchParams, instance));
		instance.afterHandler(searchParams, object);
	}

	/**
	 * 获取类字段列表.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 类字段列表
	 */
	private static List<Field> getFields(SearchParams searchParams) {
		return FieldUtil.getFieldsWithFilter(searchParams.getClass(), STATIC_FILTER, PAGE_PARAMS_FILTER);
	}

	/**
	 * 获取类字段对应的搜索方式.
	 *
	 * @param searchModel 搜索模式
	 * @return 搜索方式
	 */
	private static SearchModelType getSearchModelType(@Nullable SearchModel searchModel) {
		return searchModel == null ? SearchModelType.EQUAL_TO : searchModel.value();
	}

	/**
	 * 获取类字段对应的自定义sql.
	 *
	 * @param searchModel 搜索模式
	 * @return 自定义sql
	 */
	@Nullable
	private static String getCustomSql(@Nullable SearchModel searchModel) {
		return searchModel == null ? null : searchModel.sql();
	}

	/**
	 * 获取类字段对应的数据库字段名称.
	 *
	 * @param field 类字段
	 * @return 数据库字段名称
	 */
	private static String getColumnName(Field field) {
		Column column = field.getAnnotation(Column.class);
		return column != null && StringUtils.isNotBlank(column.name()) ? column.name() : field.getName();
	}

	/**
	 * 设置值.
	 *
	 * @param field        字段
	 * @param object       对象
	 * @param searchParams 搜索参数对象
	 * @param instance     搜索参数对象处理实例
	 */
	private static void setValue(Field field, Object object, SearchParams searchParams,
			AbstractSearchParamsUtilInstance instance) {
		SearchModel searchModel = field.getAnnotation(SearchModel.class);
		SearchModelType searchModelType = getSearchModelType(searchModel);
		if (searchModelType == SearchModelType.IGNORE) {
			return;
		}

		Object value = getValue(searchParams, field);
		if (value == null) {
			return;
		}

		List<SqlProvider> sqlProviders = getCustomerSqlProviders(searchModel, field)
				.filter(provider -> provider.accept(field.getType(), object))
				.toList();

		if (!sqlProviders.isEmpty()) {
			sqlProviders.forEach(provider -> provider.apply(object, value));
			return;
		}

		SearchParamsHandler handler = instance.getHandler(field.getType());
		if (handler == null) {
			return;
		}

		handler.setValue(object, field.getName(), getColumnName(field), value, searchModelType,
				StringUtils.trimToNull(getCustomSql(searchModel)));
	}

	/**
	 * 获取字段值.
	 *
	 * @param object 对象
	 * @param field  字段
	 * @return 值
	 */
	@Nullable
	private static Object getValue(final Object object, final Field field) {
		if (Modifier.isPublic(field.getModifiers())) {
			try {
				return field.get(object);
			}
			catch (Exception ignored) {
			}
		}

		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
			Method method = propertyDescriptor.getReadMethod();
			return method.invoke(object);
		}
		catch (Exception ignored) {
		}

		return null;
	}

	private static Stream<SqlProvider> getCustomerSqlProviders(@Nullable SearchModel searchModel, Field field) {
		if (searchModel == null) {
			return Stream.empty();
		}

		Class<? extends SqlProvider>[] providerClassArray = searchModel.sqlProviders();
		if (providerClassArray == null || providerClassArray.length <= 0) {
			return Stream.empty();
		}

		return Stream.of(providerClassArray)
				.map(SearchParamsUtils::createSqlProviderInstance)
				.filter(Objects::nonNull);
	}

	@Nullable
	private static SqlProvider createSqlProviderInstance(Class<? extends SqlProvider> providerClass) {
		SqlProvider provider = SQL_PROVIDER_CACHE.get(providerClass);
		if (provider != null) {
			return provider;
		}
		try {
			provider = providerClass.getConstructor().newInstance();
			SQL_PROVIDER_CACHE.put(providerClass, provider);
		}
		catch (Exception ignored) {
		}
		return provider;
	}
}
