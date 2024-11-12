/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

import org.springframework.web.HttpRequestMethodNotSupportedException;

import net.guerlab.cloud.core.util.EnvUtils;
import net.guerlab.cloud.web.core.exception.AbstractI18nInfo;
import net.guerlab.cloud.web.core.exception.Keys;

/**
 * 不支持的HTTP请求方式.
 *
 * @author guer
 */
public class HttpRequestMethodNotSupportedExceptionInfo extends AbstractI18nInfo {

	private static final String DEFAULT_MSG = EnvUtils.getEnv("GUERLAB_CLOUD_EXCEPTION_MESSAGE_UN_SUPPORTED_METHOD", "不支持%s请求方式");

	private final String method;

	/**
	 * 通过HttpRequestMethodNotSupportedException初始化.
	 *
	 * @param cause HttpRequestMethodNotSupportedException
	 */
	public HttpRequestMethodNotSupportedExceptionInfo(HttpRequestMethodNotSupportedException cause) {
		super(cause, 405);
		method = cause.getMethod();
	}

	@Override
	protected String getKey() {
		return Keys.HTTP_REQUEST_METHOD_NOT_SUPPORTED;
	}

	@Override
	protected Object[] getArgs() {
		return new Object[] {method};
	}

	@Override
	protected String getDefaultMessage() {
		return String.format(DEFAULT_MSG, method);
	}
}
