/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 服务加载器.
 * <br/>
 * 支持Spring、java ServiceLoader、普通类无参构造方式加载
 *
 * @param <T> 目标类类型
 * @author guer
 */
@SuppressWarnings("unused")
public class ServiceLoader<T> implements Iterable<T> {

	/**
	 * 缓存列表.
	 */
	private final List<T> cache = new CopyOnWriteArrayList<>();

	/**
	 * 目标类.
	 */
	private final Class<T> targetClass;

	/**
	 * 根据目标类创建服务加载器.
	 *
	 * @param targetClass 目标类
	 */
	public ServiceLoader(Class<T> targetClass) {
		Objects.requireNonNull(targetClass);
		this.targetClass = targetClass;
	}

	/**
	 * 根据目标类创建服务加载器.
	 *
	 * @param targetClass 目标类
	 * @param <T>         目标类类型
	 * @return 服务加载器
	 */
	public static <T> ServiceLoader<T> load(Class<T> targetClass) {
		Objects.requireNonNull(targetClass);
		return new ServiceLoader<>(targetClass);
	}

	/**
	 * 读取实例列表.
	 *
	 * @return 实例列表
	 */
	public List<T> load() {
		if (cache.isEmpty()) {
			return reload();
		}
		return Collections.unmodifiableList(cache);
	}

	/**
	 * 重新读取实例列表.
	 *
	 * @return 实例列表
	 */
	public List<T> reload() {
		List<T> list = new ArrayList<>();

		list.addAll(loadByInterfaceWithSpringApplicationContext(targetClass));
		list.addAll(loadByInterfaceWithJavaServiceLoader(targetClass));
		list.addAll(loadByClassConstructorMethod(targetClass));

		cache.clear();
		cache.addAll(list);

		return Collections.unmodifiableList(cache);
	}

	private List<T> loadByInterfaceWithSpringApplicationContext(Class<? extends T> targetClass) {
		List<T> result = new ArrayList<>();
		try {
			result.addAll(SpringUtils.getBeans(targetClass));
		}
		catch (Exception ignored) {
		}
		return result;
	}

	private List<T> loadByInterfaceWithJavaServiceLoader(Class<T> targetClass) {
		List<T> result = new ArrayList<>();
		try {
			java.util.ServiceLoader<? extends T> serviceLoader = java.util.ServiceLoader.load(targetClass);
			serviceLoader.stream().map(java.util.ServiceLoader.Provider::get).forEach(result::add);
		}
		catch (Exception ignored) {
		}
		return result;
	}

	private List<T> loadByClassConstructorMethod(Class<T> targetClass) {
		List<T> result = new ArrayList<>();
		try {
			T provider = targetClass.getConstructor().newInstance();
			result.add(provider);
		}
		catch (Exception ignored) {
		}
		return result;
	}

	@Override
	public Iterator<T> iterator() {
		return load().iterator();
	}

	@Override
	public Spliterator<T> spliterator() {
		return Spliterators.spliterator(load(), 0);
	}

	/**
	 * 获取串行流.
	 *
	 * @return 串行流
	 */
	public Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * 获取并行流.
	 *
	 * @return 并行流
	 */
	public Stream<T> parallelStream() {
		return StreamSupport.stream(spliterator(), true);
	}
}
