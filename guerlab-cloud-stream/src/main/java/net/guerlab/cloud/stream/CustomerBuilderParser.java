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

import jakarta.annotation.Nullable;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义动态方法事件构建者解析器.
 *
 * @author guer
 */
public interface CustomerBuilderParser {

	/**
	 * 根据构造方法和输入类型解析出对应的构建者.
	 * <br/>
	 * 当构造方法和输入参数无法匹配时，方法返回{@code null}
	 *
	 * @param constructor 构造方法
	 * @param inputClass  输入类型
	 * @return 构建者
	 */
	@Nullable
	DynamicsFunctionEventBuilder parse(Constructor<? extends ApplicationEvent> constructor, Class<?> inputClass);
}
