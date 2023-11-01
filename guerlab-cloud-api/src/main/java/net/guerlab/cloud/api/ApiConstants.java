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

package net.guerlab.cloud.api;

import org.springframework.lang.Nullable;

/**
 * API常量.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class ApiConstants {

	/**
	 * 服务ID.
	 */
	public static final String SERVICE_ID_PROPERTIES_PREFIX = "guerlab.cloud.api.names";

	/**
	 * 服务ID模板.
	 */
	public static final String SERVICE_ID_FORMAT = "${" + SERVICE_ID_PROPERTIES_PREFIX + ".%s:%s}";

	private ApiConstants() {
	}

	/**
	 * 获取服务ID配置Key.
	 *
	 * @param key         key
	 * @param defaultName 默认名称
	 * @return 服务ID配置Key
	 */
	public static String getServerIdKey(String key, @Nullable String defaultName) {
		return String.format(SERVICE_ID_FORMAT, key, defaultName == null ? key : defaultName);
	}
}
