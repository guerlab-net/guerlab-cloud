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
package net.guerlab.cloud.context.core.task;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;
import org.springframework.core.task.TaskDecorator;

/**
 * 授权上下文处理异步任务装饰器
 *
 * @author guer
 */
public class AuthContextHandlerTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        ContextAttributes contextAttributes = ContextAttributesHolder.get();

        return () -> {
            try {
                ContextAttributesHolder.set(contextAttributes);
                runnable.run();
            } finally {
                ContextAttributesHolder.get().clear();
            }
        };
    }
}
