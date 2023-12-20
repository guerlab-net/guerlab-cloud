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

package net.guerlab.cloud.context.core;

import java.util.HashMap;

/**
 * 上下文属性.
 *
 * @author guer
 */
public class ContextAttributes extends HashMap<Object, Object> {

	/**
	 * 保存KEY.
	 */
	public static final String KEY = ContextAttributes.class.getName();

	/**
	 * 请求保存KEY.
	 */
	public static final String REQUEST_KEY = KEY + ".request";

	/**
	 * 响应保存KEY.
	 */
	public static final String RESPONSE_KEY = KEY + ".response";

	public ContextAttributes() {
	}

	public ContextAttributes(ContextAttributes parent) {
		putAll(parent);
	}

	@Override
	public String toString() {
		return "ContextAttributes: " + super.toString();
	}

	/**
	 * 拷贝当前上下文内容属性到新的上下文属性中.
	 *
	 * @return 新上下文属性
	 */
	public ContextAttributes copy() {
		return new ContextAttributes(this);
	}
}
