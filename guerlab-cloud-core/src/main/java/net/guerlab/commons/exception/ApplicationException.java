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

package net.guerlab.commons.exception;

import java.io.Serial;
import java.util.Locale;

import jakarta.annotation.Nullable;
import lombok.Getter;

/**
 * 应用基础异常.
 *
 * @author guer
 */
@Getter
public class ApplicationException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 默认错误信息.
	 */
	public static final String DEFAULT_MSG;

	/**
	 * 错误码.
	 */
	private final int errorCode;

	static {
		Locale locale = Locale.getDefault();

		if (Locale.CHINA.equals(locale)) {
			DEFAULT_MSG = "服务器忙，请稍后再试";
		}
		else {
			DEFAULT_MSG = "The server is busy, please try again later.";
		}
	}

	/**
	 * 构造一个应用基础异常.
	 */
	public ApplicationException() {
		this(0);
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param errorCode 错误码
	 */
	public ApplicationException(int errorCode) {
		super(DEFAULT_MSG);
		this.errorCode = errorCode;
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param message            异常信息
	 * @param cause              导致的原因
	 * @param enableSuppression  启用抑制
	 * @param writableStackTrace 写入异常栈
	 */
	public ApplicationException(String message, @Nullable Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		this(message, cause, enableSuppression, writableStackTrace, 0);
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param message            异常信息
	 * @param cause              导致的原因
	 * @param enableSuppression  启用抑制
	 * @param writableStackTrace 写入异常栈
	 * @param errorCode          错误码
	 */
	public ApplicationException(String message, @Nullable Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			int errorCode) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.errorCode = errorCode;
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param message 异常信息
	 * @param cause   导致的原因
	 */
	public ApplicationException(String message, @Nullable Throwable cause) {
		this(message, cause, 0);
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param message   异常信息
	 * @param cause     导致的原因
	 * @param errorCode 错误码
	 */
	public ApplicationException(String message, @Nullable Throwable cause, int errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param message 异常信息
	 */
	public ApplicationException(String message) {
		this(message, 0);
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param message   异常信息
	 * @param errorCode 错误码
	 */
	public ApplicationException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param cause 导致的原因
	 */
	public ApplicationException(@Nullable Throwable cause) {
		this(cause, 0);
	}

	/**
	 * 构造一个应用基础异常.
	 *
	 * @param cause     导致的原因
	 * @param errorCode 错误码
	 */
	public ApplicationException(@Nullable Throwable cause, int errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

}
