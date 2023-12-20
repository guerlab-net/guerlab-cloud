/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.server.mybatis.exception.handler.builder;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;

import net.guerlab.cloud.commons.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.core.result.Fail;

/**
 * TooManyResultsException异常处理.
 *
 * @author guer
 */
public class MybatisSystemExceptionResponseBuilder extends AbstractResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return e instanceof MyBatisSystemException;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		MyBatisSystemException exception = (MyBatisSystemException) e;
		Throwable cause = exception.getCause();
		if (cause instanceof TooManyResultsException) {
			TooManyResultsExceptionResponseBuilder builder = new TooManyResultsExceptionResponseBuilder();
			builder.setMessageSource(messageSource);
			builder.setStackTracesHandler(stackTracesHandler);

			return builder.build(cause);
		}

		String message = exception.getMessage();
		if (message == null) {
			message = cause.getMessage();
		}

		Fail<Void> fail = new Fail<>(message);
		stackTracesHandler.setStackTrace(fail, e);
		return fail;
	}
}

