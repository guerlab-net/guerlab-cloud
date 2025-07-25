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

package net.guerlab.cloud.stream;

import java.lang.reflect.Constructor;
import java.util.Objects;

import jakarta.annotation.Nullable;

import org.springframework.context.ApplicationEvent;

import net.guerlab.cloud.core.util.ServiceLoader;

/**
 * 动态方法事件构建者解析器.
 *
 * @author guer
 */
public final class BuilderParser {

	private static final ServiceLoader<CustomerBuilderParser> SERVICE_LOADER = ServiceLoader.load(CustomerBuilderParser.class);

	private BuilderParser() {
	}

	/**
	 * 根据输入类型和事件类型解析出对应的构建者.
	 *
	 * @param inputClass 输入类型
	 * @param eventClass 事件类型
	 */
	static DynamicsFunctionEventBuilder parse(Class<?> inputClass, Class<? extends ApplicationEvent> eventClass) {
		@SuppressWarnings("unchecked")
		Constructor<? extends ApplicationEvent>[] constructors = (Constructor<? extends ApplicationEvent>[]) eventClass.getConstructors();

		DynamicsFunctionEventBuilder builder;
		for (Constructor<? extends ApplicationEvent> constructor : constructors) {
			builder = parseWithConstructor(constructor, inputClass);
			if (builder != null) {
				return builder;
			}
		}

		throw new IllegalArgumentException("event class %s not found effective constructor with input class: %s".formatted(eventClass, inputClass));
	}

	@Nullable
	private static DynamicsFunctionEventBuilder parseWithConstructor(Constructor<? extends ApplicationEvent> constructor, Class<?> inputClass) {
		DynamicsFunctionEventBuilder builder = parseSingleParameterConstructor(constructor, inputClass);
		if (builder != null) {
			return builder;
		}

		builder = parseDoubleParameterConstructor(constructor, inputClass);
		if (builder != null) {
			return builder;
		}

		for (CustomerBuilderParser parser : SERVICE_LOADER.load()) {
			builder = parser.parse(constructor, inputClass);
			if (builder != null) {
				return builder;
			}
		}

		return null;
	}

	@Nullable
	private static DynamicsFunctionEventBuilder parseSingleParameterConstructor(Constructor<? extends ApplicationEvent> constructor, Class<?> inputClass) {
		if (constructor.getParameterCount() != 1) {
			return null;
		}

		Class<?> singleParameterType = constructor.getParameterTypes()[0];

		if (!Objects.equals(inputClass, singleParameterType)) {
			return null;
		}

		return new SingleParameterDynamicsFunctionEventBuilder(constructor);
	}

	@Nullable
	private static DynamicsFunctionEventBuilder parseDoubleParameterConstructor(Constructor<? extends ApplicationEvent> constructor, Class<?> inputClass) {
		if (constructor.getParameterCount() != 2) {
			return null;
		}

		Class<?> first = constructor.getParameterTypes()[0];
		Class<?> second = constructor.getParameterTypes()[1];

		if (Objects.equals(inputClass, first) && Objects.equals(Object.class, second)) {
			return new DoubleParameterDynamicsFunctionEventBuilder(constructor, true);
		}
		else if (Objects.equals(inputClass, second) && Objects.equals(Object.class, first)) {
			return new DoubleParameterDynamicsFunctionEventBuilder(constructor, false);
		}

		return null;
	}
}
