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
