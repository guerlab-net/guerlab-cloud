/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.core.util.SpringUtils;
import net.guerlab.commons.reflection.FieldUtil;

/**
 * SearchParams工具类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public final class SearchParamsUtils {

	private static final Predicate<Field> STATIC_FILTER = e -> e != null && !Modifier.isStatic(e.getModifiers());

	private static final Predicate<Field> PAGE_PARAMS_FILTER = e -> !SearchParams.class.getName()
			.equals(e.getDeclaringClass().getName());

	private static final HashMap<Class<? extends AbstractSearchParamsUtilInstance>, AbstractSearchParamsUtilInstance> INSTANCES_CACHE = new HashMap<>();

	private static final HashMap<Class<? extends SqlProvider>, List<SqlProvider>> SQL_PROVIDER_CACHE = new HashMap<>();

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
		INSTANCES_CACHE.values().stream().filter(instance -> instance.accept(object))
				.forEach(instance -> handler(searchParams, object, instance));
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
				.filter(provider -> provider.accept(object, value))
				.toList();

		if (!sqlProviders.isEmpty()) {
			sqlProviders.forEach(provider -> provider.apply(object, value));
			return;
		}

		SearchParamsHandler handler = instance.getHandler(field.getType());
		if (handler == null) {
			return;
		}

		String columnName = getColumnName(field);

		JsonField jsonField = field.getAnnotation(JsonField.class);

		handler.setValue(object, field.getName(), columnName, value, searchModelType,
				StringUtils.trimToNull(getCustomSql(searchModel)), jsonField);
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
		if (providerClassArray == null || providerClassArray.length == 0) {
			return Stream.empty();
		}

		return Stream.of(providerClassArray)
				.map(SearchParamsUtils::loadSqlProviders)
				.flatMap(Collection::stream)
				.filter(Objects::nonNull);
	}

	/**
	 * 清理自定义sql提供器实例缓存.
	 *
	 * @param providerClass 自定义sql提供器类型
	 */
	public static void cleanSqlProviderCache(Class<? extends SqlProvider> providerClass) {
		SQL_PROVIDER_CACHE.remove(providerClass);
	}

	/**
	 * 获取自定义sql提供器实例列表.
	 *
	 * @param providerClass 自定义sql提供器类型
	 * @return 自定义sql提供器实例列表
	 */
	public static List<SqlProvider> loadSqlProviders(Class<? extends SqlProvider> providerClass) {
		List<SqlProvider> providers = SQL_PROVIDER_CACHE.get(providerClass);
		if (providers != null) {
			return providers;
		}

		if (providerClass.isInterface()) {
			providers = loadSqlProvidersByInterface(providerClass);
		}
		else {
			providers = loadSqlProvidersByClassConstructorMethod(providerClass);
		}

		providers = providers.stream().sorted(Comparator.comparingInt(SqlProvider::getOrder)).toList();

		if (!providers.isEmpty()) {
			SQL_PROVIDER_CACHE.put(providerClass, providers);
		}

		return providers;
	}

	private static List<SqlProvider> loadSqlProvidersByInterface(Class<? extends SqlProvider> providerClass) {
		List<SqlProvider> providers = new ArrayList<>();
		providers.addAll(loadSqlProvidersByInterfaceWithSpringApplicationContext(providerClass));
		providers.addAll(loadSqlProvidersByInterfaceWithJavaServiceLoader(providerClass));
		return providers;
	}

	private static List<SqlProvider> loadSqlProvidersByInterfaceWithSpringApplicationContext(Class<? extends SqlProvider> providerClass) {
		List<SqlProvider> result = new ArrayList<>();
		try {
			result.addAll(SpringUtils.getBeans(providerClass));
			log.debug("load SqlProvider by Spring, {} -> {}", providerClass.getName(), result.size());
		}
		catch (Exception ignored) {
		}
		return result;

	}

	private static List<SqlProvider> loadSqlProvidersByInterfaceWithJavaServiceLoader(Class<? extends SqlProvider> providerClass) {
		List<SqlProvider> result = new ArrayList<>();
		try {
			ServiceLoader<? extends SqlProvider> serviceLoader = ServiceLoader.load(providerClass);
			serviceLoader.stream().map(ServiceLoader.Provider::get).forEach(result::add);
			log.debug("load SqlProvider by ServiceLoader, {} -> {}", providerClass.getName(), result.size());
		}
		catch (Exception ignored) {
		}
		return result;
	}

	private static List<SqlProvider> loadSqlProvidersByClassConstructorMethod(Class<? extends SqlProvider> providerClass) {
		List<SqlProvider> result = new ArrayList<>();
		try {
			SqlProvider provider = providerClass.getConstructor().newInstance();
			result.add(provider);
			log.debug("load SqlProvider by Class Constructor Method, {} -> {}", providerClass.getName(), result.size());
		}
		catch (Exception ignored) {
		}
		return result;
	}
}
