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

package net.guerlab.cloud.web.core.exception.handler.builder;

import java.util.Collection;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.RequestParamsError;
import net.guerlab.cloud.web.core.exception.handler.AbstractRequestParamsErrorResponseBuilder;

/**
 * ConstraintViolationException异常处理.
 *
 * @author guer
 */
public class ConstraintViolationExceptionResponseBuilder extends AbstractRequestParamsErrorResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return e instanceof ConstraintViolationException;
	}

	@Override
	public Fail<Collection<String>> build(Throwable e) {
		ConstraintViolationException exception = (ConstraintViolationException) e;

		Collection<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

		Collection<String> displayMessageList = constraintViolations.stream().map(ConstraintViolation::getMessage)
				.toList();
		return build0(new RequestParamsError(exception, displayMessageList));
	}
}
