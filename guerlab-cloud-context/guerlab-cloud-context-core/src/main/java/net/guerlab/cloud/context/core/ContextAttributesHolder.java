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

package net.guerlab.cloud.context.core;

import java.util.List;
import java.util.ServiceLoader;

import jakarta.annotation.Nullable;

import org.springframework.core.NamedInheritableThreadLocal;

/**
 * 上下文属性持有器.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class ContextAttributesHolder {

	private static final InheritableThreadLocal<ContextAttributes> THREAD_LOCAL = new NamedInheritableThreadLocal<>("ContextAttributes");

	private static final List<ObjectContextAttributesHolder> OBJECT_CONTEXT_ATTRIBUTES_HOLDERS = ServiceLoader.load(
			ObjectContextAttributesHolder.class).stream().map(ServiceLoader.Provider::get).toList();

	private static final List<OriginalContextProvider> ORIGINAL_CONTEXT_PROVIDERS = ServiceLoader.load(
			OriginalContextProvider.class).stream().map(ServiceLoader.Provider::get).toList();

	private ContextAttributesHolder() {

	}

	/**
	 * 获取获取上下文属性.
	 *
	 * @return 上下文属性
	 */
	public static ContextAttributes get() {
		return get(getOriginalContext());
	}

	/**
	 * 从原始上下文上获取获取上下文属性.
	 *
	 * @param originalContext 原始上下文
	 * @return 上下文属性
	 */
	public static ContextAttributes get(@Nullable Object originalContext) {
		ContextAttributes contextAttributes = null;
		if (originalContext != null) {
			contextAttributes = OBJECT_CONTEXT_ATTRIBUTES_HOLDERS.stream()
					.filter(holder -> holder.accept(originalContext))
					.map(holder -> holder.get(originalContext)).findFirst().orElse(null);
		}
		if (contextAttributes == null) {
			contextAttributes = THREAD_LOCAL.get();
			if (contextAttributes == null) {
				contextAttributes = new ContextAttributes("ContextAttributesHolder-" + Thread.currentThread()
						.getName());
			}
		}
		set(contextAttributes, originalContext);
		return contextAttributes;
	}

	/**
	 * 设置当前线程的上下文属性.
	 *
	 * @param contextAttributes 上下文属性
	 */
	public static void set(ContextAttributes contextAttributes) {
		set(contextAttributes, getOriginalContext());
	}

	/**
	 * 从原始上下文上设置当前线程的上下文属性.
	 *
	 * @param contextAttributes 上下文属性
	 * @param originalContext   原始上下文
	 */
	@SuppressWarnings("unused")
	public static void set(ContextAttributes contextAttributes, @Nullable Object originalContext) {
		if (originalContext != null) {
			List<ObjectContextAttributesHolder> holders = OBJECT_CONTEXT_ATTRIBUTES_HOLDERS.stream()
					.filter(holder -> holder.accept(originalContext)).toList();

			holders.forEach(holder -> holder.set(originalContext, contextAttributes));
		}

		THREAD_LOCAL.set(contextAttributes);
	}

	/**
	 * 获取原始上下文对象.
	 *
	 * @return 原始上下文对象
	 */
	@Nullable
	private static Object getOriginalContext() {
		Object originalContext;
		for (OriginalContextProvider provider : ORIGINAL_CONTEXT_PROVIDERS) {
			originalContext = provider.get();
			if (originalContext != null) {
				return originalContext;
			}
		}

		return null;
	}
}
