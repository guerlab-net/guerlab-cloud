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

package net.guerlab.cloud.commons.exception;

import java.io.Serial;

import net.guerlab.cloud.core.exception.AbstractI18nApplicationException;

/**
 * 匹配结果不唯一异常.
 *
 * @author guer
 */
public class ResultIsNotOnlyOneException extends AbstractI18nApplicationException {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE_KEY = "message.exception.commons.resultIsNotOnlyOne";

	/**
	 * 结果数量.
	 */
	private final String size;

	/**
	 * 创建匹配结果不唯一异常.
	 *
	 * @param size 结果数量
	 */
	public ResultIsNotOnlyOneException(String size) {
		this.size = size;
	}

	@Override
	protected String getKey() {
		return MESSAGE_KEY;
	}

	@Override
	protected Object[] getArgs() {
		return new Object[] {size};
	}
}
