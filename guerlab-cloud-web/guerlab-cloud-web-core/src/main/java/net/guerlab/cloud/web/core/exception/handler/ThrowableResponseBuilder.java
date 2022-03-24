/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import org.apache.commons.lang3.StringUtils;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.DefaultExceptionInfo;

/**
 * 通用异常处理.
 *
 * @author guer
 */
public class ThrowableResponseBuilder extends AbstractI18nResponseBuilder {

	@Override
	public boolean accept(Throwable e) {
		return true;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

		Fail<Void> fail;
		if (responseStatus != null) {
			int errorCode = responseStatus.value().value();
			String message = responseStatus.reason();

			fail = new Fail<>(getMessage(message), errorCode);
			stackTracesHandler.setStackTrace(fail, e.getCause());
		}
		else if (StringUtils.isBlank(e.getMessage())) {
			fail = buildByI18nInfo(new DefaultExceptionInfo(e), e);
		}
		else {
			fail = new Fail<>(getMessage(e.getLocalizedMessage()));
			stackTracesHandler.setStackTrace(fail, e.getCause());
		}
		return fail;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
