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

package net.guerlab.cloud.gateway.exception;

import org.springframework.core.Ordered;

/**
 * 异常信息处理.
 *
 * @author guer
 */
public interface ExceptionMessageHandler extends Ordered, Comparable<ExceptionMessageHandler> {

	/**
	 * 是否允许处理.
	 *
	 * @param error 错误信息
	 * @return 是否允许处理
	 */
	boolean accept(Throwable error);

	/**
	 * 获取异常信息.
	 *
	 * @param error 错误
	 * @return 异常信息
	 */
	String getMessage(Throwable error);

	/**
	 * 获取异常码.
	 *
	 * @param error 错误
	 * @return 异常码
	 */
	default int getErrorCode(Throwable error) {
		return 0;
	}

	/**
	 * 排序实现.
	 *
	 * @param o 其他异常信息提供者
	 * @return 排序结果
	 */
	@Override
	default int compareTo(ExceptionMessageHandler o) {
		return this.getOrder() - o.getOrder();
	}
}
