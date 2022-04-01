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

package net.guerlab.cloud.commons.exception.handler;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.core.result.Fail;

/**
 * 堆栈处理.
 *
 * @author guer
 */
public interface StackTracesHandler {

	/**
	 * 设置堆栈信息.
	 *
	 * @param fail      响应结果
	 * @param throwable 异常信息
	 */
	void setStackTrace(Fail<?> fail, @Nullable Throwable throwable);
}
