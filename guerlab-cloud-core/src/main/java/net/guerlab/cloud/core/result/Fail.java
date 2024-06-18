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

package net.guerlab.cloud.core.result;

import jakarta.annotation.Nullable;

import net.guerlab.cloud.core.util.EnvUtils;

/**
 * 通用失败JSON返回结果集.
 *
 * @param <T> 数据类型
 * @author guer
 */
public class Fail<T> extends Result<T> {

	/**
	 * 默认消息.
	 */
	public static final String MSG = EnvUtils.getEnv("GUERLAB_CLOUD_RESULT_MESSAGE_FAIL", "fail");

	/**
	 * 无参构造.
	 */
	public Fail() {
		this(MSG, null);
	}

	/**
	 * 通过设置消息内容来初始化结果集.
	 *
	 * @param message 消息内容
	 */
	public Fail(@Nullable String message) {
		this(message, null);
	}

	/**
	 * 通过设置数据来初始化结果集.
	 *
	 * @param data 数据
	 */
	public Fail(@Nullable T data) {
		this(MSG, data);
	}

	/**
	 * 通过设置消息内容和数据来初始化结果集.
	 *
	 * @param message 消息内容
	 * @param data    数据
	 */
	public Fail(@Nullable String message, @Nullable T data) {
		super(false, message, data);
	}

	/**
	 * 通过设置数据来初始化结果集.
	 *
	 * @param message   消息内容
	 * @param errorCode 错误代码
	 */
	public Fail(@Nullable String message, int errorCode) {
		this(message, null);
		this.errorCode = errorCode;
	}

	/**
	 * 通过设置数据来初始化结果集.
	 *
	 * @param message     消息内容
	 * @param errorCode   错误代码
	 * @param errorDetail 错误详情
	 */
	public Fail(@Nullable String message, int errorCode, @Nullable String errorDetail) {
		this(message, null);
		this.errorCode = errorCode;
		this.errorDetail = errorDetail;
	}
}
