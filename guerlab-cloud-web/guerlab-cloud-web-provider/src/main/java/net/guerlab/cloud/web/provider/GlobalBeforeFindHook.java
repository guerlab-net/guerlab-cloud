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

package net.guerlab.cloud.web.provider;

import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 全局查询前置处理钩子.
 *
 * @author guer
 */
public interface GlobalBeforeFindHook {

	/**
	 * 是否允许处理该对象.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 是否允许处理
	 */
	boolean accept(SearchParams searchParams);

	/**
	 * 搜索前置处理.
	 *
	 * @param searchParams 搜索参数对象
	 */
	void handler(SearchParams searchParams);
}
