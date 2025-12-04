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

package net.guerlab.cloud.stream;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationEvent;

/**
 * 双参数构造器动态方法事件构建者实现.
 *
 * @author guer
 */
public class DoubleParameterDynamicsFunctionEventBuilder extends DynamicsFunctionEventBuilder {

	private final Constructor<? extends ApplicationEvent> constructor;

	private final boolean firstParameterIsObjectType;

	/**
	 * 初始化双参数构造器动态方法事件构建者实现.
	 *
	 * @param constructor                构造方法
	 * @param firstParameterIsObjectType 第一个参数是否为Object类型
	 */
	public DoubleParameterDynamicsFunctionEventBuilder(Constructor<? extends ApplicationEvent> constructor, boolean firstParameterIsObjectType) {
		this.constructor = constructor;
		this.firstParameterIsObjectType = firstParameterIsObjectType;
	}

	@Override
	public ApplicationEvent buildEvent(DynamicsFunction source, Object input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (firstParameterIsObjectType) {
			return constructor.newInstance(input, source);
		}
		return constructor.newInstance(source, input);
	}
}
