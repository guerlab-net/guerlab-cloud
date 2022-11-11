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

package net.guerlab.cloud.web.webmvc.exception;

import java.util.Locale;

import org.springframework.web.bind.MissingServletRequestParameterException;

import net.guerlab.cloud.web.core.exception.AbstractI18nInfo;
import net.guerlab.cloud.web.core.exception.Keys;

/**
 * 缺失请求参数.
 *
 * @author guer
 */
public class MissingServletRequestParameterExceptionInfo extends AbstractI18nInfo {

	private static final String DEFAULT_MSG;

	static {
		Locale locale = Locale.getDefault();

		if (Locale.CHINA.equals(locale)) {
			DEFAULT_MSG = "请求参数[%s]缺失";
		}
		else {
			DEFAULT_MSG = "Request parameter [%s] missing.";
		}
	}

	private final String parameterName;

	/**
	 * 通过MissingServletRequestParameterException初始化.
	 *
	 * @param cause MissingServletRequestParameterException
	 */
	public MissingServletRequestParameterExceptionInfo(MissingServletRequestParameterException cause) {
		super(cause, 403);
		parameterName = cause.getParameterName();
	}

	@Override
	protected String getKey() {
		return Keys.MISSING_SERVLET_REQUEST_PARAMETER;
	}

	@Override
	protected Object[] getArgs() {
		return new Object[] {parameterName};
	}

	@Override
	protected String getDefaultMessage() {
		return String.format(DEFAULT_MSG, parameterName);
	}
}
