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

package net.guerlab.cloud.server.mybatis.exception.handler.builder;

import org.apache.ibatis.exceptions.TooManyResultsException;

import net.guerlab.cloud.commons.exception.ResultIsNotOnlyOneException;
import net.guerlab.cloud.commons.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.core.result.Fail;

/**
 * TooManyResultsException异常处理.
 *
 * @author guer
 */
public class TooManyResultsExceptionResponseBuilder extends AbstractResponseBuilder {

	static {
		MybatisSystemExceptionResponseBuilder.addBuilder(TooManyResultsException.class, TooManyResultsExceptionResponseBuilder.class);
	}

	@Override
	public boolean accept(Throwable e) {
		return e instanceof TooManyResultsException;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		TooManyResultsException exception = (TooManyResultsException) e;
		String message = exception.getMessage();
		message = message.replace("Expected one result (or null) to be returned by selectOne(), but found: ", "")
				.trim();

		Fail<Void> fail = new Fail<>(new ResultIsNotOnlyOneException(message).getMessage(messageSource));
		stackTracesHandler.setStackTrace(fail, e);
		return fail;
	}
}

