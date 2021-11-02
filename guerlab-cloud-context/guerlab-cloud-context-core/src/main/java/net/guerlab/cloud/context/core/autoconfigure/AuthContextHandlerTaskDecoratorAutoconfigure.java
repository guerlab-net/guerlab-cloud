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
