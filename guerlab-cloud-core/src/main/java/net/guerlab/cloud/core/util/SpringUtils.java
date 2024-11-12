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

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;
import lombok.Getter;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;

/**
 * Spring工具类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@AutoConfiguration
public class SpringUtils implements ApplicationContextAware {

	/**
	 * ApplicationContext.
	 */
	@Getter
	private static ApplicationContext context = null;

	/**
	 * 根据bean类型获取bean实例列表.
	 *
	 * @param clazz bean类型
	 * @param <T>   bean类型
	 * @return bean实例列表
	 */
	public static <T> Collection<T> getBeans(Class<T> clazz) {
		return getContext().getBeansOfType(clazz).values();
	}

	/**
	 * 根据bean类型获取bean实例.
	 *
	 * @param clazz bean类型
	 * @param <T>   bean类型
	 * @return bean实例
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getContext().getBean(clazz);
	}

	/**
	 * 根据注解类型获取bean实例.
	 *
	 * @param annotationType 注解类型
	 * @return bean实例和实例名称散列表
	 */
	public static Map<String, Object> getBeanMapWithAnnotation(Class<? extends Annotation> annotationType) {
		return getContext().getBeansWithAnnotation(annotationType);
	}

	/**
	 * 根据注解类型获取bean实例.
	 *
	 * @param clazz          实例类型
	 * @param annotationType 注解类型
	 * @param <T>            实例类型
	 * @return bean实例和实例名称散列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getBeanMapWithAnnotation(Class<T> clazz,
			Class<? extends Annotation> annotationType) {
		Map<String, Object> beanMap = getBeanMapWithAnnotation(annotationType);
		if (beanMap.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, T> result = new HashMap<>(beanMap.size());
		for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
			String beanName = entry.getKey();
			Object bean = entry.getValue();

			if (clazz.isInstance(bean)) {
				result.put(beanName, (T) bean);
			}
		}

		return result;
	}

	/**
	 * 根据注解类型获取bean实例.
	 *
	 * @param annotationType 注解类型
	 * @return bean实例列表
	 */
	public static Collection<Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return getBeanMapWithAnnotation(annotationType).values();
	}

	/**
	 * 根据注解类型获取bean实例.
	 *
	 * @param clazz          bean类型
	 * @param annotationType 注解类型
	 * @param <T>            bean类型
	 * @return bean实例列表
	 */
	public static <T> Collection<T> getBeansWithAnnotation(Class<T> clazz, Class<? extends Annotation> annotationType) {
		return getBeanMapWithAnnotation(clazz, annotationType).values();
	}

	/**
	 * 根据指定类型获取bean实例和实例名称.
	 *
	 * @param clazz bean实例类
	 * @param <T>   bean实例类型
	 * @return bean实例和实例名称散列表
	 */
	public static <T> Map<String, T> getBeanMap(Class<T> clazz) {
		return context.getBeansOfType(clazz);
	}

	/**
	 * 获取环境信息.
	 *
	 * @return 环境信息
	 */
	private static Environment getEnvironment() {
		return getContext().getEnvironment();
	}

	/**
	 * 获取属性.
	 *
	 * @param propertyName 属性名
	 * @return 属性值
	 */
	@Nullable
	public static String getProperty(String propertyName) {
		return getEnvironment().getProperty(propertyName);
	}

	/**
	 * 获取应用名称.
	 *
	 * @return 应用名称
	 */
	public static String getApplicationName() {
		String name = getProperty("spring.application.name");
		return name == null ? "" : name;
	}

	/**
	 * 推送事件.
	 *
	 * @param event 事件
	 */
	public static void publishEvent(ApplicationEvent event) {
		getContext().publishEvent(event);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
