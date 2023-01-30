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

package net.guerlab.cloud.web.core.exception;

import java.io.Serial;
import java.util.Collection;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.bind.MethodArgumentNotValidException;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 请求参数错误.
 *
 * @author guer
 */
@Getter
public class RequestParamsError extends ApplicationException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 错误消息列表.
	 */
	private final Collection<String> errors;

	/**
	 * 通过消息内容和ConstraintViolationException初始化.
	 *
	 * @param cause              ConstraintViolationException
	 * @param displayMessageList 错误消息列表
	 */
	public RequestParamsError(ConstraintViolationException cause, Collection<String> displayMessageList) {
		super(getMessage(displayMessageList), cause, 400);
		errors = displayMessageList;
	}

	/**
	 * 通过消息内容和MethodArgumentNotValidException初始化.
	 *
	 * @param cause              MethodArgumentNotValidException
	 * @param displayMessageList 错误消息列表
	 */
	public RequestParamsError(MethodArgumentNotValidException cause, Collection<String> displayMessageList) {
		super(getMessage(displayMessageList), cause, 400);
		errors = displayMessageList;
	}

	private static String getMessage(Collection<String> displayMessageList) {
		if (CollectionUtil.isEmpty(displayMessageList)) {
			return "";
		}
		return StringUtils.joinWith("\n", displayMessageList.toArray());
	}
}
