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

import org.springframework.web.servlet.resource.NoResourceFoundException;

import net.guerlab.cloud.core.util.EnvUtils;
import net.guerlab.cloud.web.core.exception.AbstractI18nInfo;
import net.guerlab.cloud.web.core.exception.Keys;

/**
 * 未发现处理程序(404).
 *
 * @author guer
 */
public class NoResourceFoundExceptionInfo extends AbstractI18nInfo {

	private static final String DEFAULT_MSG = EnvUtils.getEnv("GUERLAB_CLOUD_EXCEPTION_MESSAGE_NOT_FOUND", "请求地址无效[%s %s]");

	private final String method;

	private final String url;

	/**
	 * 通过NoResourceFoundException初始化.
	 *
	 * @param cause NoResourceFoundException
	 */
	public NoResourceFoundExceptionInfo(NoResourceFoundException cause) {
		super(cause, 404);
		method = cause.getHttpMethod().name();
		url = cause.getResourcePath();
	}

	@Override
	protected String getKey() {
		return Keys.NO_HANDLER_FOUND;
	}

	@Override
	protected Object[] getArgs() {
		return new Object[] {method, url};
	}

	@Override
	protected String getDefaultMessage() {
		return String.format(DEFAULT_MSG, method, url);
	}
}
