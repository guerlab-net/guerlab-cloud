/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.exception.handler;

import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;

import net.guerlab.cloud.core.result.Fail;

/**
 * 异常信息构建者.
 *
 * @author guer
 */
public interface ResponseBuilder extends Ordered, Comparable<ResponseBuilder> {

	/**
	 * 判断是否允许处理.
	 *
	 * @param e 异常
	 * @return 是否允许处理
	 */
	boolean accept(Throwable e);

	/**
	 * 构建异常响应.
	 *
	 * @param e 异常
	 * @return 异常响应
	 */
	Fail<?> build(Throwable e);

	/**
	 * 设置消息源.
	 *
	 * @param messageSource 消息源
	 */
	void setMessageSource(MessageSource messageSource);

	/**
	 * 设置堆栈处理.
	 *
	 * @param stackTracesHandler 堆栈处理
	 */
	void setStackTracesHandler(StackTracesHandler stackTracesHandler);

	/**
	 * 排序处理.
	 *
	 * @param o 其他异常信息构建者
	 * @return 排序结果
	 */
	@Override
	default int compareTo(ResponseBuilder o) {
		return getOrder() - o.getOrder();
	}
}
