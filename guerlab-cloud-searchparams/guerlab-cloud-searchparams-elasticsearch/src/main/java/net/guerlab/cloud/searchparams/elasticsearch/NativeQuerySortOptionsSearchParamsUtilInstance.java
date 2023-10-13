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

package net.guerlab.cloud.searchparams.elasticsearch;

import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import net.guerlab.cloud.searchparams.AbstractSearchParamsUtilInstance;

/**
 * 原生查询排序选项处理实例.
 *
 * @author guer
 */
public class NativeQuerySortOptionsSearchParamsUtilInstance extends AbstractSearchParamsUtilInstance {

	private static final Class<?> CLAZZ = NativeQueryBuilder.class;

	public NativeQuerySortOptionsSearchParamsUtilInstance() {
		setDefaultHandler(new SortOptionsBuilderDefaultHandler());
	}

	@Override
	public boolean accept(Object object) {
		return CLAZZ.isInstance(object);
	}
}
