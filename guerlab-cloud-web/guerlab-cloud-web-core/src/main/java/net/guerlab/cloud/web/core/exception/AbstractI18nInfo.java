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

package net.guerlab.cloud.web.core.exception;

import java.util.Locale;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 抽象国际化错误消息.
 *
 * @author guer
 */
public abstract class AbstractI18nInfo {

	private static final String EMPTY_KEY = "";

	/**
	 * 错误码.
	 */
	final int errorCode;

	/**
	 * 异常.
	 */
	final Throwable throwable;

	/**
	 * 通过异常和错误码初始化.
	 *
	 * @param throwable 异常
	 * @param errorCode 错误码
	 */
	protected AbstractI18nInfo(@Nullable Throwable throwable, int errorCode) {
		this.throwable = throwable;
		this.errorCode = errorCode;
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
		return throwable != null ? throwable.getLocalizedMessage() : this.getClass().getName();
	}

	/**
	 * 获取错误码.
	 *
	 * @return 错误码
	 */
	public final int getErrorCode() {
		return errorCode;
	}

	/**
	 * 获取异常.
	 *
	 * @return 异常
	 */
	@Nullable
	public final Throwable getThrowable() {
		return throwable;
	}

}
