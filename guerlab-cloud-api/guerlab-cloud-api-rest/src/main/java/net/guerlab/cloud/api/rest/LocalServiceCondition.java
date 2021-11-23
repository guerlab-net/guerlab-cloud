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

package net.guerlab.cloud.api.rest;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * 本地服务加载条件
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class LocalServiceCondition implements Condition {

    private static final ClassLoader CLASS_LOADER = LocalServiceCondition.class.getClassLoader();

    /**
     * 类名称列表
     */
    private final Collection<String> classNames;

    /**
     * 根据类名称列表构造本地服务加载条件
     *
     * @param classNames
     *         类名称列表
     */
    public LocalServiceCondition(Collection<String> classNames) {
        if (classNames.isEmpty()) {
            throw new IllegalArgumentException("classNames disallow be empty");
        }
        this.classNames = classNames;
    }

    @Override
    public final boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        return classNames.stream().allMatch(this::hasClass);
    }

    /**
     * 判断是否有指定类
     *
     * @param className
     *         类名称
     * @return 是否有指定类
     */
    private boolean hasClass(String className) {
        try {
            return CLASS_LOADER.loadClass(className) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
