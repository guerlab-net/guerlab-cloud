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

package net.guerlab.cloud.web.webmvc.exception.handler.builder;

import org.springframework.web.bind.MissingServletRequestParameterException;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.handler.AbstractI18nResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.MissingServletRequestParameterExceptionInfo;

/**
 * MissingServletRequestParameterException异常处理.
 *
 * @author guer
 */
public class MissingServletRequestParameterExceptionResponseBuilder extends AbstractI18nResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return e instanceof MissingServletRequestParameterException;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
		return buildByI18nInfo(new MissingServletRequestParameterExceptionInfo(exception), e);
	}
}
