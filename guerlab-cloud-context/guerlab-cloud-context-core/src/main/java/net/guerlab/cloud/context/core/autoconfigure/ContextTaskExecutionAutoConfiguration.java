/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;

import net.guerlab.cloud.context.core.task.ContextHandlerTaskDecorator;

/**
 * 上下文任务执行器自动配置.
 *
 * @author guer
 */
@AutoConfiguration(
		before = TaskExecutionAutoConfiguration.class
)
public class ContextTaskExecutionAutoConfiguration {

	/**
	 * 创建上下文处理异步任务装饰器.
	 *
	 * @return 上下文处理异步任务装饰器
	 */
	@Bean
	@ConditionalOnMissingBean(TaskDecorator.class)
	public ContextHandlerTaskDecorator contextHandlerTaskDecorator() {
		return new ContextHandlerTaskDecorator();
	}
}
