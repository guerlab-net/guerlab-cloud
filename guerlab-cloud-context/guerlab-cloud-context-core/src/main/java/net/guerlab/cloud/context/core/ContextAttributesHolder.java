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

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * 上下文属性持有器
 *
 * @author guer
 */
public class ContextAttributesHolder {

    private final static InheritableThreadLocal<ContextAttributes> THREAD_LOCAL = new InheritableThreadLocal<>() {

        @Override
        protected ContextAttributes initialValue() {
            return new ContextAttributes();
        }
    };

    private final static List<ObjectContextAttributesHolder> OBJECT_CONTEXT_ATTRIBUTES_HOLDERS = ServiceLoader.load(
            ObjectContextAttributesHolder.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());

    private ContextAttributesHolder() {

    }

    /**
     * 获取获取上下文属性
     *
     * @return 上下文属性
     */
    public static ContextAttributes get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 从指定对象上获取获取上下文属性
     *
     * @param object
     *         指定对象
     * @return 上下文属性
     */
    public static ContextAttributes get(@Nullable Object object) {
        ContextAttributes contextAttributes = null;
        if (object != null) {
            contextAttributes = OBJECT_CONTEXT_ATTRIBUTES_HOLDERS.stream().filter(holder -> holder.accept(object)).map(holder -> holder.get(object)).findFirst().orElse(null);
        }
        if (contextAttributes == null) {
            contextAttributes = THREAD_LOCAL.get();
        }
        return contextAttributes;
    }

    /**
     * 从指定对象上设置当前线程的上下文属性
     *
     * @param object
     *         指定对象
     * @param contextAttributes
     *         上下文属性
     */
    public static void set(ContextAttributes contextAttributes, @Nullable Object object) {
        if (object == null) {
            THREAD_LOCAL.set(contextAttributes);
        } else {
            List<ObjectContextAttributesHolder> holders = OBJECT_CONTEXT_ATTRIBUTES_HOLDERS.stream().filter(holder -> holder.accept(object)).collect(Collectors.toList());

            if (holders.isEmpty()) {
                THREAD_LOCAL.set(contextAttributes);
            } else {
                holders.forEach(holder -> holder.set(object, contextAttributes));
            }
        }
    }

    /**
     * 设置当前线程的上下文属性
     *
     * @param contextAttributes
     *         上下文属性
     */
    public static void set(ContextAttributes contextAttributes) {
        THREAD_LOCAL.set(contextAttributes);
    }
}
