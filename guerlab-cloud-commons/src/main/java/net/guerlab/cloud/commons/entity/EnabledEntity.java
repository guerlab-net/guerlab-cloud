/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.entity;

import jakarta.annotation.Nullable;

/**
 * 可启用对象接口.
 *
 * @author guer
 */
public interface EnabledEntity {

	/**
	 * 获取是否启用.
	 *
	 * @return 是否启用
	 */
	@Nullable
	Boolean enabled();

	/**
	 * 设置是否启用.
	 *
	 * @param enabled 是否启用
	 */
	void enabled(@Nullable Boolean enabled);

	/**
	 * 是否启用默认处理.
	 */
	default void enabledDefaultHandler() {
		if (enabled() == null) {
			enabled(true);
		}
	}
}
