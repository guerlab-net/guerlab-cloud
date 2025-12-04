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

package net.guerlab.cloud.web.core.exception.handler;

import java.util.Collection;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.RequestParamsError;

/**
 * 抽象请求参数错误响应构建.
 *
 * @author guer
 */
public abstract class AbstractRequestParamsErrorResponseBuilder extends AbstractI18nResponseBuilder {

	/**
	 * 根据请求参数构建异常信息.
	 *
	 * @param e 请求参数异常
	 * @return 信息信息
	 */
	protected Fail<Collection<String>> build0(RequestParamsError e) {
		String message = getMessage(e.getLocalizedMessage());
		Fail<Collection<String>> fail = new Fail<>(message, e.getErrorCode());
		fail.setData(e.getErrors());
		stackTracesHandler.setStackTrace(fail, e);
		return fail;
	}
}
