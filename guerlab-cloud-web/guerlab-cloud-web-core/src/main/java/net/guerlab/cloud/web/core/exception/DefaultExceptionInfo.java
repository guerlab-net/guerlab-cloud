/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

/**
 * 默认错误.
 *
 * @author guer
 */
public class DefaultExceptionInfo extends AbstractI18nInfo {

	private static final String DEFAULT_MSG;

	static {
		Locale locale = Locale.getDefault();

		if (Locale.CHINA.equals(locale)) {
			DEFAULT_MSG = "系统繁忙,请稍后再试";
		}
		else {
			DEFAULT_MSG = "The system is busy. Please try again later.";
		}
	}

	/**
	 * 通过异常信息初始化.
	 *
	 * @param cause Throwable
	 */
	public DefaultExceptionInfo(Throwable cause) {
		super(cause, 500);
	}

	@Override
	protected String getKey() {
		return Keys.DEFAULT;
	}

	@Override
	protected String getDefaultMessage() {
		return DEFAULT_MSG;
	}
}
