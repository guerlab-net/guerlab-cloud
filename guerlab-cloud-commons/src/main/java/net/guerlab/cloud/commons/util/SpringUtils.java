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
package net.guerlab.cloud.commons.util;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * spring 工具类
 *
 * @author guer
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class SpringUtils {

    private SpringUtils() {

    }

    /**
     * 获取环境信息
     *
     * @return 环境信息
     */
    private static Environment getEnvironment() {
        return SpringApplicationContextUtil.getContext().getEnvironment();
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public static String getApplicationName() {
        String name = getProperty("spring.application.name");
        return name == null ? "" : name;
    }

    /**
     * 获取属性
     *
     * @param propertyName
     *         属性名
     * @return 属性值
     */
    @Nullable
    public static String getProperty(String propertyName) {
        return getEnvironment().getProperty(propertyName);
    }

    /**
     * 根据bean类型获取bean实例列表
     *
     * @param clazz
     *         bean类型
     * @param <T>
     *         bean类型
     * @return bean实例列表
     */
    public static <T> Collection<T> getBeans(Class<T> clazz) {
        return SpringApplicationContextUtil.getContext().getBeansOfType(clazz).values();
    }

    /**
     * 根据bean类型获取bean实例
     *
     * @param clazz
     *         bean类型
     * @param <T>
     *         bean类型
     * @return bean实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return SpringApplicationContextUtil.getContext().getBean(clazz);
    }

    /**
     * 根据注解类型获取bean实例
     *
     * @param annotationType
     *         注解类型
     * @return bean实例列表
     */
    public static Collection<Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return SpringApplicationContextUtil.getContext().getBeansWithAnnotation(annotationType).values();
    }

    /**
     * 根据注解类型获取bean实例
     *
     * @param clazz
     *         bean类型
     * @param annotationType
     *         注解类型
     * @param <T>
     *         bean类型
     * @return bean实例列表
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> getBeansWithAnnotation(Class<T> clazz, Class<? extends Annotation> annotationType) {
        Collection<Object> beans = getBeansWithAnnotation(annotationType);
        if (beans.isEmpty()) {
            return Collections.emptyList();
        }

        return beans.stream().filter(clazz::isInstance).map(bean -> (T) bean).collect(Collectors.toList());
    }
}
