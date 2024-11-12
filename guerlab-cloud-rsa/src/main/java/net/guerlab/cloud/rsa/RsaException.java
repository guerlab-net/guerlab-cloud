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

package net.guerlab.cloud.rsa;

/**
 * RSA异常.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class RsaException extends RuntimeException {

	/**
	 * 构造RSA异常.
	 */
	public RsaException() {
	}

	/**
	 * 构造RSA异常.
	 *
	 * @param message 异常信息
	 */
	public RsaException(String message) {
		super(message);
	}

	/**
	 * 构造RSA异常.
	 *
	 * @param message 异常信息
	 * @param cause   异常源
	 */
	public RsaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造RSA异常.
	 *
	 * @param cause 异常源
	 */
	public RsaException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造RSA异常.
	 *
	 * @param message            异常信息
	 * @param cause              异常源
	 * @param enableSuppression  是否启用抑制
	 * @param writableStackTrace 是否可写堆栈跟踪
	 */
	public RsaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
