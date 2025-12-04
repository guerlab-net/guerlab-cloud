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

package net.guerlab.cloud.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 基础搜索参数.
 *
 * @author guer
 */
@Data
@Schema(name = "BaseSearchParams", description = "基础对象搜索参数")
public class BaseSearchParams implements SearchParams {

	/**
	 * 排序字段列表.
	 */
	@Schema(description = "排序字段列表")
	private OrderBys orderBys;
}
