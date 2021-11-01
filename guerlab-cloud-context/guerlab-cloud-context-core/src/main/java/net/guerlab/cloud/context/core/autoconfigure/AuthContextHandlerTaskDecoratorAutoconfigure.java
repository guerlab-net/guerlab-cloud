package net.guerlab.cloud.context.core.autoconfigure;

import net.guerlab.cloud.context.core.task.AuthContextHandlerTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;

/**
 * 授权上下文处理异步任务装饰器自动配置
 *
 * @author guer
 */
@Configuration
public class AuthContextHandlerTaskDecoratorAutoconfigure {

    @Bean
    public TaskDecorator authContextHandlerTaskDecorator() {
        return new AuthContextHandlerTaskDecorator();
    }
}
