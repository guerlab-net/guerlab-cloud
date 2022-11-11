/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

import org.springframework.web.HttpRequestMethodNotSupportedException;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.handler.AbstractI18nResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.HttpRequestMethodNotSupportedExceptionInfo;

/**
 * HttpRequestMethodNotSupportedException异常处理.
 *
 * @author guer
 */
public class HttpRequestMethodNotSupportedExceptionResponseBuilder extends AbstractI18nResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return e instanceof HttpRequestMethodNotSupportedException;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
		return buildByI18nInfo(new HttpRequestMethodNotSupportedExceptionInfo(exception), e);
	}
}
