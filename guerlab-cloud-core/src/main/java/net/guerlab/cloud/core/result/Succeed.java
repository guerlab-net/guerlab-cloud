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

package net.guerlab.cloud.core.result;

import org.springframework.lang.Nullable;

/**
 * 通用成功JSON返回结果集.
 *
 * @param <T> 数据类型
 * @author guer
 */
@SuppressWarnings("unused")
public class Succeed<T> extends Result<T> {

	/**
	 * 默认消息.
	 */
	public static final String MSG = "success";

	/**
	 * 无参构造.
	 */
	public Succeed() {
		this(MSG, null);
	}

	/**
	 * 通过设置消息内容来初始化结果集.
	 *
	 * @param message 消息内容
	 */
	public Succeed(@Nullable String message) {
		this(message, null);
	}

	/**
	 * 通过设置数据来初始化结果集.
	 *
	 * @param data 数据
	 */
	public Succeed(@Nullable T data) {
		this(MSG, data);
	}

	/**
	 * 通过设置消息内容和数据来初始化结果集.
	 *
	 * @param message 消息内容
	 * @param data    数据
	 */
	public Succeed(@Nullable String message, @Nullable T data) {
		super(true, message, data);
	}
}
