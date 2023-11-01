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

package net.guerlab.cloud.commons.i18n;

/**
 * 多消息源处理提供者.
 *
 * @author guer
 */
@FunctionalInterface
public interface MultiMessageSourceProvider {

	/**
	 * 获取消息源.
	 *
	 * @return 消息源
	 */
	String get();
}
