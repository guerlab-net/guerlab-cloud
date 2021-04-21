/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.commons.util;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.core.env.Environment;

import java.util.Collection;

/**
 * spring 工具类
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
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
        return getEnvironment().getProperty("spring.application.name");
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
}
