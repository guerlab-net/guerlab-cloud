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

package net.guerlab.commons.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import jakarta.annotation.Nullable;

/**
 * 集合工具类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class CollectionUtil {

	private CollectionUtil() {
	}

	/**
	 * 是否为空集合.
	 *
	 * @param coll 集合
	 * @return 是否为空
	 */
	public static boolean isEmpty(@Nullable Collection<?> coll) {
		return coll == null || coll.isEmpty();
	}

	/**
	 * iterable是否为空.
	 *
	 * @param iterable iterable
	 * @return 是否为空
	 */
	public static boolean isEmpty(@Nullable Iterable<?> iterable) {
		return iterable == null || isEmpty(iterable.iterator());
	}

	/**
	 * iterator是否为空.
	 *
	 * @param iterator iterator
	 * @return 是否为空
	 */
	public static boolean isEmpty(@Nullable Iterator<?> iterator) {
		return iterator == null || !iterator.hasNext();
	}

	/**
	 * map是否为空.
	 *
	 * @param map map
	 * @return 是否为空
	 */
	public static boolean isEmpty(@Nullable Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * Enumeration是否为空.
	 *
	 * @param enumeration Enumeration
	 * @return 是否为空
	 */
	public static boolean isEmpty(@Nullable Enumeration<?> enumeration) {
		return enumeration == null || !enumeration.hasMoreElements();
	}

	/**
	 * 是否为非空集合.
	 *
	 * @param coll 集合
	 * @return 否为非空
	 */
	public static boolean isNotEmpty(@Nullable Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * iterable是否为非空.
	 *
	 * @param iterable iterable
	 * @return 否为非空
	 */
	public static boolean isNotEmpty(@Nullable Iterable<?> iterable) {
		return !isEmpty(iterable);
	}

	/**
	 * iterator是否为非空.
	 *
	 * @param iterator iterator
	 * @return 否为非空
	 */
	public static boolean isNotEmpty(@Nullable Iterator<?> iterator) {
		return !isEmpty(iterator);
	}

	/**
	 * map是否为非空.
	 *
	 * @param map map
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * Enumeration是否为非空.
	 *
	 * @param enumeration Enumeration
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(@Nullable Enumeration<?> enumeration) {
		return !isEmpty(enumeration);
	}

	/**
	 * 判断是否为空集合,将排除null元素.
	 *
	 * @param coll 集合
	 * @return 是否为空集合
	 */
	public static boolean isBlank(@Nullable Collection<?> coll) {
		return isEmpty(coll) || coll.stream().noneMatch(Objects::nonNull);
	}

	/**
	 * 判断是否为非空集合,将排除null元素.
	 *
	 * @param coll 集合
	 * @return 否为非空集合
	 */
	public static boolean isNotBlank(@Nullable Collection<?> coll) {
		return !isBlank(coll);
	}

	/**
	 * 集合遍历执行某方法.
	 *
	 * @param <T>      集合元素类型
	 * @param iterable 集合
	 * @param action   集合元素执行方法
	 */
	public static <T> void forEach(Iterable<T> iterable, Consumer<? super T> action) {
		stream(iterable).forEach(action);
	}

	/**
	 * 集合转换为Map结构.
	 *
	 * @param <T>       集合元素类型
	 * @param <K>       map结果集key类型
	 * @param iterable  集合
	 * @param keyMapper key映射关系
	 * @return Map结构数据
	 */
	public static <T, K> Map<K, T> toMap(
			@Nullable Collection<T> iterable,
			Function<T, K> keyMapper) {
		return toMap(iterable, keyMapper, Function.identity());
	}

	/**
	 * 集合转换为Map结构.
	 *
	 * @param <T>       集合元素类型
	 * @param <K>       map结果集key类型
	 * @param iterable  集合
	 * @param keyMapper key映射关系
	 * @param filters   过滤器链
	 * @return Map结构数据
	 */
	public static <T, K> Map<K, T> toMap(
			@Nullable Collection<T> iterable,
			Function<T, K> keyMapper,
			@Nullable Collection<Predicate<? super T>> filters) {
		return toMap(iterable, keyMapper, Function.identity(), filters);
	}

	/**
	 * 集合转换为Map结构.
	 *
	 * @param <T>         集合元素类型
	 * @param <K>         map结果集key类型
	 * @param <U>         map结果集value类型
	 * @param iterable    集合
	 * @param keyMapper   key映射关系
	 * @param valueMapper value映射关系
	 * @return Map结构数据
	 */
	public static <T, K, U> Map<K, U> toMap(
			@Nullable Collection<T> iterable,
			Function<T, K> keyMapper,
			Function<T, U> valueMapper) {
		return toMap(iterable, keyMapper, valueMapper, throwingMerger(), HashMap::new);
	}

	/**
	 * 集合转换为Map结构.
	 *
	 * @param <T>         集合元素类型
	 * @param <K>         map结果集key类型
	 * @param <U>         map结果集value类型
	 * @param iterable    集合
	 * @param keyMapper   key映射关系
	 * @param valueMapper value映射关系
	 * @param filters     过滤器链
	 * @return Map结构数据
	 */
	public static <T, K, U> Map<K, U> toMap(
			@Nullable Collection<T> iterable,
			Function<T, K> keyMapper,
			Function<T, U> valueMapper,
			@Nullable Collection<Predicate<? super T>> filters) {
		return toMap(iterable, keyMapper, valueMapper, throwingMerger(), HashMap::new, filters);
	}

	/**
	 * 集合转换为Map结构.
	 *
	 * @param <T>           集合元素类型
	 * @param <K>           map结果集key类型
	 * @param <U>           map结果集value类型
	 * @param <M>           map结果集类型
	 * @param iterable      集合
	 * @param keyMapper     key映射关系
	 * @param valueMapper   value映射关系
	 * @param mergeFunction 值合并方法
	 * @param mapSupplier   map构造方法
	 * @return Map结构数据
	 */
	public static <T, K, U, M extends Map<K, U>> Map<K, U> toMap(
			@Nullable Iterable<T> iterable,
			Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends U> valueMapper,
			@Nullable BinaryOperator<U> mergeFunction,
			Supplier<M> mapSupplier) {
		return toMap(iterable, keyMapper, valueMapper, mergeFunction, mapSupplier, null);
	}

	/**
	 * 集合转换为Map结构.
	 *
	 * @param <T>           集合元素类型
	 * @param <K>           map结果集key类型
	 * @param <U>           map结果集value类型
	 * @param <M>           map结果集类型
	 * @param iterable      集合
	 * @param keyMapper     key映射关系
	 * @param valueMapper   value映射关系
	 * @param mergeFunction 值合并方法
	 * @param mapSupplier   map构造方法
	 * @param filters       过滤器链
	 * @return Map结构数据
	 */
	public static <T, K, U, M extends Map<K, U>> Map<K, U> toMap(
			@Nullable Iterable<T> iterable,
			Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends U> valueMapper,
			@Nullable BinaryOperator<U> mergeFunction,
			Supplier<M> mapSupplier,
			@Nullable Collection<Predicate<? super T>> filters) {
		if (isEmpty(iterable)) {
			return Collections.emptyMap();
		}
		if (mergeFunction == null) {
			mergeFunction = throwingMerger();
		}

		return filters(stream(iterable), filters)
				.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));
	}

	/**
	 * 集合转变为list集合,不转换的内容可在映射关系中返回null来过滤该值.
	 *
	 * @param <I>      输入集合元素类型
	 * @param <O>      输出list元素类型
	 * @param iterable 集合
	 * @param mapper   映射关系
	 * @return list集合
	 */
	public static <I, O> List<O> toList(@Nullable Iterable<I> iterable, Function<I, O> mapper) {
		return toList(iterable, mapper, null);
	}

	/**
	 * 集合转变为list集合,不转换的内容可在映射关系中返回null来过滤该值.
	 *
	 * @param <I>      输入集合元素类型
	 * @param <O>      输出list元素类型
	 * @param iterable 集合
	 * @param mapper   映射关系
	 * @param filters  过滤器列表
	 * @return list集合
	 */
	public static <I, O> List<O> toList(@Nullable Iterable<I> iterable, Function<I, O> mapper,
			@Nullable Collection<Predicate<? super O>> filters) {
		return toCollection(iterable, mapper, false, ArrayList::new, filters);
	}

	/**
	 * 集合转变为list集合并去重,不转换的内容可在映射关系中返回null来过滤该值.
	 *
	 * @param <I>      输入集合元素类型
	 * @param <O>      输出list元素类型
	 * @param iterable 集合
	 * @param mapper   映射关系
	 * @return list集合
	 */
	public static <I, O> List<O> toDistinctList(@Nullable Iterable<I> iterable, Function<I, O> mapper) {
		return toDistinctList(iterable, mapper, null);
	}

	/**
	 * 集合转变为list集合并去重,不转换的内容可在映射关系中返回null来过滤该值.
	 *
	 * @param <I>      输入集合元素类型
	 * @param <O>      输出list元素类型
	 * @param iterable 集合
	 * @param mapper   映射关系
	 * @param filters  过滤器列表
	 * @return list集合
	 */
	public static <I, O> List<O> toDistinctList(@Nullable Iterable<I> iterable, Function<I, O> mapper,
			@Nullable Collection<Predicate<? super O>> filters) {
		return toCollection(iterable, mapper, true, ArrayList::new, filters);
	}

	/**
	 * 集合转变为set集合,不转换的内容可在映射关系中返回null来过滤该值.
	 *
	 * @param <I>      输入集合元素类型
	 * @param <O>      输出set元素类型
	 * @param iterable 集合
	 * @param mapper   映射关系
	 * @return set集合
	 */
	public static <I, O> Set<O> toSet(@Nullable Iterable<I> iterable, Function<I, O> mapper) {
		return toSet(iterable, mapper, null);
	}

	/**
	 * 集合转变为set集合,不转换的内容可在映射关系中返回null来过滤该值.
	 *
	 * @param <I>      输入集合元素类型
	 * @param <O>      输出set元素类型
	 * @param iterable 集合
	 * @param mapper   映射关系
	 * @param filters  过滤器列表
	 * @return set集合
	 */
	public static <I, O> Set<O> toSet(
			@Nullable Iterable<I> iterable,
			Function<I, O> mapper,
			@Nullable Collection<Predicate<? super O>> filters) {
		return toCollection(iterable, mapper, false, HashSet::new, filters);
	}

	/**
	 * 集合转换.
	 *
	 * @param <I>               输入元素类型
	 * @param <O>               输出元素类型
	 * @param <C>               集合类型
	 * @param iterable          集合
	 * @param mapper            映射关系
	 * @param collectionFactory 集合工厂类
	 * @param filters           过滤器列表
	 * @return 集合
	 */
	public static <I, O, C extends Collection<O>> C toCollection(
			@Nullable Iterable<I> iterable,
			Function<I, O> mapper,
			Supplier<C> collectionFactory,
			@Nullable Collection<Predicate<? super O>> filters) {
		return toCollection(iterable, mapper, false, collectionFactory, filters);
	}

	/**
	 * 集合转换.
	 *
	 * @param <I>               输入元素类型
	 * @param <O>               输出元素类型
	 * @param <C>               集合类型
	 * @param iterable          集合
	 * @param mapper            映射关系
	 * @param distinct          是否去重
	 * @param collectionFactory 集合工厂类
	 * @param filters           过滤器列表
	 * @return 集合
	 */
	public static <I, O, C extends Collection<O>> C toCollection(
			@Nullable Iterable<I> iterable,
			Function<I, O> mapper,
			boolean distinct,
			Supplier<C> collectionFactory,
			@Nullable Collection<Predicate<? super O>> filters) {
		if (isEmpty(iterable)) {
			return collectionFactory.get();
		}

		Stream<O> stream = filters(stream(iterable).map(mapper).filter(Objects::nonNull), filters);

		if (distinct) {
			stream = stream.distinct();
		}

		return stream.collect(Collectors.toCollection(collectionFactory));
	}

	/**
	 * 将集合进行分组.
	 *
	 * @param <E>      输入集合元素类型
	 * @param <K>      分组key类型
	 * @param iterable 集合
	 * @param mapper   分组关系
	 * @return 分组后的集合map
	 */
	public static <K, E> Map<K, List<E>> group(@Nullable Iterable<E> iterable, Function<E, K> mapper) {
		if (isEmpty(iterable)) {
			return Collections.emptyMap();
		}

		return stream(iterable).collect(Collectors.groupingBy(mapper));
	}

	/**
	 * 过滤元素,为null的值将默认过滤.
	 *
	 * @param <T>      集合元素类型
	 * @param iterable 待过滤集合
	 * @param filters  过滤器集合
	 * @return 过滤后集合
	 */
	public static <T> List<T> filters(@Nullable Iterable<T> iterable, @Nullable Collection<Predicate<? super T>> filters) {
		if (isEmpty(iterable)) {
			return new ArrayList<>();
		}

		return filters(stream(iterable).filter(Objects::nonNull), filters).collect(Collectors.toList());
	}

	/**
	 * 对一个数据流进行过滤.
	 *
	 * @param stream  数据流
	 * @param filters 过滤器链
	 * @param <T>     数据类型
	 * @return 过滤后的数据流
	 */
	public static <T> Stream<T> filters(Stream<T> stream, @Nullable Collection<Predicate<? super T>> filters) {
		if (filters != null && !filters.isEmpty()) {
			for (Predicate<? super T> filter : filters) {
				stream = stream.filter(filter);
			}
		}

		return stream;
	}

	private static <E> Stream<E> stream(Iterable<E> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false).filter(Objects::nonNull);
	}

	private static <V> BinaryOperator<V> throwingMerger() {
		return (u, v) -> {
			throw new IllegalStateException(String.format("Duplicate key %s", u));
		};
	}
}
