package net.guerlab.cloud.context.core.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 授权异步配置
 *
 * @author guer
 */
@Configuration
@AutoConfigureAfter(AuthContextHandlerTaskDecoratorAutoconfigure.class)
public class AuthAsyncConfigurer implements AsyncConfigurer {

    /**
     * 异步任务装饰器
     */
    private final TaskDecorator taskDecorator;

    /**
     * 根据异步任务装饰器初始化
     *
     * @param taskDecorator
     *         异步任务装饰器
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public AuthAsyncConfigurer(TaskDecorator taskDecorator) {
        this.taskDecorator = taskDecorator;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(taskDecorator);
        executor.initialize();
        return executor;
    }
}
