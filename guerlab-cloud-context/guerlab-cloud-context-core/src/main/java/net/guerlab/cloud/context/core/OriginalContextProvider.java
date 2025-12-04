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

package net.guerlab.cloud.context.core;

import jakarta.annotation.Nullable;

/**
 * 原始上下文提供.
 *
 * @author guer
 */
public interface OriginalContextProvider {

	/**
	 * 获取原始上下文对象.
	 *
	 * @return 原始上下文对象
	 */
	@Nullable
	Object get();
}
