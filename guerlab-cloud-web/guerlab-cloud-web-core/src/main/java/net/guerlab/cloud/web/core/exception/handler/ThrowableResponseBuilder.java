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

package net.guerlab.cloud.web.core.exception.handler;

import org.apache.commons.lang3.StringUtils;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.DefaultExceptionInfo;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;

/**
 * 通用异常处理.
 *
 * @author guer
 */
public class ThrowableResponseBuilder extends AbstractI18nResponseBuilder {

	private final GlobalExceptionProperties properties;

	public ThrowableResponseBuilder(GlobalExceptionProperties properties) {
		this.properties = properties;
	}

	@Override
	public boolean accept(Throwable e) {
		return true;
	}

	@Override
	public Fail<Void> build(Throwable e) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

		String overrideExceptionTemplate = StringUtils.trimToNull(properties.getOverrideExceptionTemplate(e.getClass()));

		Fail<Void> fail;
		if (responseStatus != null) {
			fail = new Fail<>(getMessage(responseStatus.reason()), responseStatus.value().value());
		}
		else if (overrideExceptionTemplate != null) {
			fail = new Fail<>(overrideExceptionTemplate.formatted(e.getLocalizedMessage()));
		}
		else if (StringUtils.isBlank(e.getMessage()) || properties.isRewriteNonApplicationExceptions()) {
			fail = buildByI18nInfo(new DefaultExceptionInfo(e), e);
		}
		else {
			fail = new Fail<>(getMessage(e.getLocalizedMessage()));
		}

		if (fail.getStackTraces().isEmpty()) {
			stackTracesHandler.setStackTrace(fail, e.getCause() == null ? e : e.getCause());
		}

		return fail;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
