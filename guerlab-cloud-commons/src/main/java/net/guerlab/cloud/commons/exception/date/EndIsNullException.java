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

package net.guerlab.cloud.commons.exception.date;

import java.io.Serial;

import net.guerlab.cloud.core.exception.AbstractI18nApplicationException;

/**
 * 结束日期和日期偏移量不能都为空.
 *
 * @author guer
 */
public class EndIsNullException extends AbstractI18nApplicationException {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE_KEY = "message.exception.date.endIsNull";

	@Override
	protected String getKey() {
		return MESSAGE_KEY;
	}

	@Override
	public int getErrorCode() {
		return 401;
	}
}
