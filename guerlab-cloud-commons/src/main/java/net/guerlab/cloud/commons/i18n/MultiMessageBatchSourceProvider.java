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

package net.guerlab.cloud.commons.i18n;

import java.util.List;

/**
 * 多消息源处理批量提供者.
 *
 * @author guer
 */
@FunctionalInterface
public interface MultiMessageBatchSourceProvider {

	/**
	 * 获取消息源列表.
	 *
	 * @return 消息源列表
	 */
	List<String> get();
}
