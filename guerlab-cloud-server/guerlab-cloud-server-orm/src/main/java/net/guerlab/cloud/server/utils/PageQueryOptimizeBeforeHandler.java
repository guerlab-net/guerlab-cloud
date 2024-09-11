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

package net.guerlab.cloud.server.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.guerlab.cloud.searchparams.PageQueryOptimize;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 分页搜索优化处理.
 *
 * @author guer
 */
public class PageQueryOptimizeBeforeHandler implements SelectPageBeforeHandler {

	@Override
	public void beforeQuery(QueryWrapper<?> wrapper, SearchParams searchParams, Page<?> page) {
		if (searchParams instanceof PageQueryOptimize pageQueryOptimize) {
			Boolean allowSelectCount = pageQueryOptimize.allowSelectCount();
			if (allowSelectCount != null) {
				page.setSearchCount(allowSelectCount);
			}
		}
	}
}
