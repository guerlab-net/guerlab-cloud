/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.stream;

import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

/**
 * 动态方法.
 *
 * @author guer
 */
@Slf4j
public class DynamicsFunction implements IDynamicsFunction, ApplicationContextAware {

	private final DynamicsFunctionEventBuilder builder;

	private final String functionName;

	private ApplicationContext applicationContext;

	/**
	 * 创建动态方法.
	 *
	 * @param functionName 方法名.
	 * @param inputClass   输入类型
	 * @param eventClass   事件类型
	 */
	public DynamicsFunction(String functionName, Class<?> inputClass, Class<? extends ApplicationEvent> eventClass) {
		Assert.hasText(functionName, () -> "functionName cannot be empty");
		this.functionName = functionName;
		this.builder = BuilderParser.parse(inputClass, eventClass);
	}

	@Override
	public void invoke(Object input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		log.debug("invoke[functionName={}, input={}]", functionName, input);

		ApplicationEvent event = builder.buildEvent(this, input);
		applicationContext.publishEvent(event);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
