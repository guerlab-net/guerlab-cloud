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

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.springframework.lang.Nullable;

/**
 * 通用JSON返回结果集.
 *
 * @param <T> 数据类型
 * @author guer
 */
@Data
@SuppressWarnings("unused")
public class Result<T> {

	/**
	 * 响应状态.
	 */
	protected boolean status;

	/**
	 * 消息.
	 */
	protected String message;

	/**
	 * 数据.
	 */
	protected T data;

	/**
	 * 错误码.
	 */
	protected int errorCode;

	/**
	 * 错误详情.
	 */
	protected String errorDetail;

	/**
	 * 堆栈跟踪列表.
	 */
	protected List<ApplicationStackTrace> stackTraces = new ArrayList<>();

	/**
	 * 无参构造.
	 */
	public Result() {
		this(null, null);
	}

	/**
	 * 通过设置消息内容来初始化结果集.
	 *
	 * @param message 消息内容
	 */
	public Result(@Nullable String message) {
		this(false, message, null);
	}

	/**
	 * 通过设置消息内容和数据来初始化结果集.
	 *
	 * @param message 消息内容
	 * @param data    数据
	 */
	public Result(@Nullable String message, @Nullable T data) {
		this(false, message, data);
	}

	/**
	 * 通过设置消息内容和数据来初始化结果集.
	 *
	 * @param message   消息内容
	 * @param errorCode 错误码
	 */
	public Result(@Nullable String message, int errorCode) {
		this(false, message, null, errorCode);
	}

	/**
	 * 通过设置响应状态、消息内容和数据来初始化结果集.
	 *
	 * @param status  响应状态
	 * @param message 消息内容
	 * @param data    数据
	 */
	public Result(boolean status, @Nullable String message, @Nullable T data) {
		this(status, message, data, 0);
	}

	/**
	 * 通过设置响应状态、消息内容和数据来初始化结果集.
	 *
	 * @param status    响应状态
	 * @param message   消息内容
	 * @param data      数据
	 * @param errorCode 错误码
	 */
	public Result(boolean status, @Nullable String message, @Nullable T data, int errorCode) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.errorCode = errorCode;
	}

	/**
	 * 添加堆栈跟踪列表.
	 *
	 * @param stackTraces 堆栈跟踪列表
	 * @return 通用JSON返回结果集
	 */
	public Result<T> addStackTraces(@Nullable List<ApplicationStackTrace> stackTraces) {
		if (stackTraces != null && this.stackTraces != null) {
			this.stackTraces.addAll(stackTraces);
		}
		return this;
	}

	/**
	 * 添加堆栈跟踪.
	 *
	 * @param stackTrace 堆栈跟踪
	 * @return 通用JSON返回结果集
	 */
	public Result<T> addStackTraces(@Nullable ApplicationStackTrace stackTrace) {
		if (stackTrace != null && stackTraces != null) {
			stackTraces.add(stackTrace);
		}
		return this;
	}
}
