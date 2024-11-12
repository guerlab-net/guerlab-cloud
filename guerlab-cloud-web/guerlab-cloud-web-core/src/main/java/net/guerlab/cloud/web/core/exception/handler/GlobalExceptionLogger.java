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

/**
 * 全局异常处理日志记录器.
 *
 * @author guer
 */
public interface GlobalExceptionLogger {

	/**
	 * 记录日志.
	 *
	 * @param e             异常
	 * @param requestMethod 请求方法
	 * @param requestPath   请求路径
	 */
	void debug(Throwable e, String requestMethod, String requestPath);
}
