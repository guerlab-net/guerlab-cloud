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

import org.springframework.web.servlet.resource.NoResourceFoundException;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.handler.AbstractI18nResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.NoResourceFoundExceptionInfo;

/**
 * NoResourceFoundException异常处理.
 *
 * @author guer
 */
public class NoResourceFoundExceptionInfoBuilder extends AbstractI18nResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return e instanceof NoResourceFoundException;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		NoResourceFoundException exception = (NoResourceFoundException) e;
		return buildByI18nInfo(new NoResourceFoundExceptionInfo(exception), e);
	}
}
