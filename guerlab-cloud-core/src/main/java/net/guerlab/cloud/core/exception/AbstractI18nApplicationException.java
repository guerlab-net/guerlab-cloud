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

package net.guerlab.cloud.core.exception;

import java.io.Serial;
import java.util.Locale;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象国际化异常信息.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public abstract class AbstractI18nApplicationException extends ApplicationException {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final String EMPTY_KEY = "";

	/**
	 * 构造一个国际化异常信息.
	 */
	protected AbstractI18nApplicationException() {
		super();
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param errorCode 错误码
	 */
	protected AbstractI18nApplicationException(int errorCode) {
		super(errorCode);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param message            异常信息
	 * @param cause              导致的原因
	 * @param enableSuppression  启用抑制
	 * @param writableStackTrace 写入异常栈
	 */
	protected AbstractI18nApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param message            异常信息
	 * @param cause              导致的原因
	 * @param enableSuppression  启用抑制
	 * @param writableStackTrace 写入异常栈
	 * @param errorCode          错误码
	 */
	protected AbstractI18nApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, int errorCode) {
		super(message, cause, enableSuppression, writableStackTrace, errorCode);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param message 异常信息
	 * @param cause   导致的原因
	 */
	protected AbstractI18nApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param message   异常信息
	 * @param cause     导致的原因
	 * @param errorCode 错误码
	 */
	protected AbstractI18nApplicationException(String message, Throwable cause, int errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param message 异常信息
	 */
	protected AbstractI18nApplicationException(String message) {
		super(message);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param message   异常信息
	 * @param errorCode 错误码
	 */
	protected AbstractI18nApplicationException(String message, int errorCode) {
		super(message, errorCode);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param cause 导致的原因
	 */
	protected AbstractI18nApplicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造一个国际化异常信息.
	 *
	 * @param cause     导致的原因
	 * @param errorCode 错误码
	 */
	protected AbstractI18nApplicationException(Throwable cause, int errorCode) {
		super(cause, errorCode);
	}

	/**
	 * 获取国际化处理后内容.
	 *
	 * @param messageSource messageSource
	 * @return 国际化处理后内容
	 */
	@Nullable
	public final String getMessage(MessageSource messageSource) {
		String key = getKey();

		if (StringUtils.isBlank(key)) {
			return getDefaultMessage();
		}

		Locale locale = LocaleContextHolder.getLocale();

		return messageSource.getMessage(key, getArgs(), getDefaultMessage(), locale);
	}

	/**
	 * 获取国际化key.
	 *
	 * @return 国际化key
	 */
	protected String getKey() {
		return EMPTY_KEY;
	}

	/**
	 * 获取国际化参数列表.
	 *
	 * @return 国际化参数列表
	 */
	protected Object[] getArgs() {
		return new Object[0];
	}

	/**
	 * 获取默认显示错误信息.
	 *
	 * @return 默认显示错误信息
	 */
	protected String getDefaultMessage() {
		return getLocalizedMessage();
	}
}
