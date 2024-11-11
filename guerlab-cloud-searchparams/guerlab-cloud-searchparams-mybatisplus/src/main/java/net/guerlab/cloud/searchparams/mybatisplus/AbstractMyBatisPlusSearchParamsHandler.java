/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.searchparams.mybatisplus;

import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchParamsHandler;

/**
 * mybatis plus参数处理.
 *
 * @author guer
 */
public abstract class AbstractMyBatisPlusSearchParamsHandler implements SearchParamsHandler {

	/**
	 * 获取允许处理的数据类型.
	 *
	 * @return 允许处理的数据类型
	 */
	public abstract Class<?> acceptClass();

	protected String getJsonPath(JsonField jsonField) {
		String jsonPath = StringUtils.trimToEmpty(jsonField.jsonPath());
		if (jsonPath == null) {
			jsonPath = "$";
		}
		return jsonPath;
	}
}
