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

package net.guerlab.cloud.api.exception;

import feign.codec.DecodeException;

import net.guerlab.cloud.commons.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.commons.exception.ApplicationException;

/**
 * feign解析异常处理.
 *
 * @author guer
 */
public class FeignDecodeExceptionResponseBuilder extends AbstractResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return e instanceof DecodeException && e.getCause() instanceof ApplicationException;
	}

	@Override
	public Fail<?> build(Throwable e) {
		ApplicationException cause = (ApplicationException) e.getCause();
		Fail<Void> fail = new Fail<>(cause.getMessage(), cause.getErrorCode());
		stackTracesHandler.setStackTrace(fail, e);
		return fail;
	}
}
