/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import java.util.Map;

import org.slf4j.MDC;

import org.springframework.core.task.TaskDecorator;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;

/**
 * 授权上下文处理异步任务装饰器.
 *
 * @author guer
 */
public class AuthContextHandlerTaskDecorator implements TaskDecorator {

	@Override
	public Runnable decorate(Runnable runnable) {
		// 切换线程后，主线程退出会导致上下文被清理
		Map<String, String> mdcContextMap = MDC.getCopyOfContextMap();
		ContextAttributes newContextAttributes = ContextAttributesHolder.get().copy();

		return () -> {
			try {
				MDC.setContextMap(mdcContextMap);
				ContextAttributesHolder.set(newContextAttributes);
				runnable.run();
			}
			finally {
				ContextAttributesHolder.get().clear();
				MDC.clear();
			}
		};
	}
}
