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

package net.guerlab.cloud.gateway.exception;

import org.springframework.core.Ordered;

/**
 * 默认异常信息处理.
 *
 * @author guer
 */
public class DefaultExceptionMessageHandler implements ExceptionMessageHandler {

	@Override
	public boolean accept(Throwable error) {
		return true;
	}

	@Override
	public String getMessage(Throwable error) {
		return error.getLocalizedMessage();
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
