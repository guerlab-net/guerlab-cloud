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

package net.guerlab.cloud.web.webmvc.exception;

import org.springframework.web.bind.MissingServletRequestParameterException;

import net.guerlab.cloud.core.util.EnvUtils;
import net.guerlab.cloud.web.core.exception.AbstractI18nInfo;
import net.guerlab.cloud.web.core.exception.Keys;

/**
 * 缺失请求参数.
 *
 * @author guer
 */
public class MissingServletRequestParameterExceptionInfo extends AbstractI18nInfo {

	private static final String DEFAULT_MSG = EnvUtils.getEnv("GUERLAB_CLOUD_EXCEPTION_MESSAGE_PARAMETER_MISSING", "请求参数[%s]缺失");

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
