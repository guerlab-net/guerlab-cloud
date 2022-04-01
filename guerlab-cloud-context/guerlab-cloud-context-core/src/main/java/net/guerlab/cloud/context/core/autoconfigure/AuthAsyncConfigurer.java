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

import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 授权异步配置.
 *
 * @author guer
 */
@Configuration
@AutoConfigureAfter(AuthContextHandlerTaskDecoratorAutoconfigure.class)
public class AuthAsyncConfigurer implements AsyncConfigurer {

	/**
	 * 异步任务装饰器.
	 */
	private final TaskDecorator taskDecorator;

	/**
	 * 根据异步任务装饰器初始化.
	 *
	 * @param taskDecorator 异步任务装饰器
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
