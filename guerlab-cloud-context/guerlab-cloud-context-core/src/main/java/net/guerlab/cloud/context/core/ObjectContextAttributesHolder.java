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
package net.guerlab.cloud.context.core;

/**
 * 基于对象的上下文属性持有器
 *
 * @author guer
 */
public interface ObjectContextAttributesHolder {

    /**
     * 是否允许处理
     *
     * @param object
     *         对象
     * @return 是否允许处理
     */
    boolean accept(Object object);

    /**
     * 获取上下文属性
     *
     * @param object
     *         对象
     * @return 上下文属性
     */
    ContextAttributes get(Object object);

    /**
     * 设置当前线程的上下文属性
     *
     * @param object
     *         对象
     * @param contextAttributes
     *         上下文属性
     */
    void set(Object object, ContextAttributes contextAttributes);
}
