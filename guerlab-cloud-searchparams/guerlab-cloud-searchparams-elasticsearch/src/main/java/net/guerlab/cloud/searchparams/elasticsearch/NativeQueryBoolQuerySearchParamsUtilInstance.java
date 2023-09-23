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

package net.guerlab.cloud.searchparams.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;

import net.guerlab.cloud.searchparams.AbstractSearchParamsUtilInstance;

/**
 * 原生查询布尔查询条件处理实例.
 *
 * @author guer
 */
public class NativeQueryBoolQuerySearchParamsUtilInstance extends AbstractSearchParamsUtilInstance {

	private static final Class<?> CLAZZ = BoolQuery.Builder.class;

	/**
	 * 初始化ElasticSearch处理实例.
	 */
	public NativeQueryBoolQuerySearchParamsUtilInstance() {
		setDefaultHandler(new BoolQueryBuilderDefaultHandler());
	}

	@Override
	public boolean accept(Object object) {
		return CLAZZ.isInstance(object);
	}
}
