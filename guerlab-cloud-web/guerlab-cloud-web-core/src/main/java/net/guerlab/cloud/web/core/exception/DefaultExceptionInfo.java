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

import net.guerlab.cloud.core.util.EnvUtils;

/**
 * 默认错误.
 *
 * @author guer
 */
public class DefaultExceptionInfo extends AbstractI18nInfo {

	/**
	 * 默认异常信息.
	 */
	public static final String DEFAULT_MSG = EnvUtils.getEnv("GUERLAB_CLOUD_EXCEPTION_MESSAGE_DEFAULT", "系统繁忙,请稍后再试");

	/**
	 * 默认异常码.
	 */
	public static final int DEFAULT_ERROR_CODE = 500;

	/**
	 * 通过异常信息初始化.
	 *
	 * @param cause Throwable
	 */
	public DefaultExceptionInfo(Throwable cause) {
		super(cause, DEFAULT_ERROR_CODE);
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
