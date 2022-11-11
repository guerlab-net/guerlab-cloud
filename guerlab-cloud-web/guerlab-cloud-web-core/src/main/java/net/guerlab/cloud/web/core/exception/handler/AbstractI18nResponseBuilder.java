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

package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.commons.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.AbstractI18nInfo;

/**
 * 抽象国际化异常信息构建者.
 *
 * @author guer
 */
public abstract class AbstractI18nResponseBuilder extends AbstractResponseBuilder {

	/**
	 * 根据国际化信息构建异常信息.
	 *
	 * @param i18nInfo 国际化信息
	 * @param e        异常
	 * @return 异常信息
	 */
	protected Fail<Void> buildByI18nInfo(AbstractI18nInfo i18nInfo, Throwable e) {
		String message = i18nInfo.getMessage(messageSource);
		Fail<Void> fail = new Fail<>(message, i18nInfo.getErrorCode());
		stackTracesHandler.setStackTrace(fail, e);
		return fail;
	}
}
