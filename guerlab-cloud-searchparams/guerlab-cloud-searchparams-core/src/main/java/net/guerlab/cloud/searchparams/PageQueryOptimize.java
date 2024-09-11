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

package net.guerlab.cloud.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

/**
 * 分页查询优化.
 *
 * @author guer
 */
@Schema(name = "PageQueryOptimize", description = "分页查询优化")
public interface PageQueryOptimize extends SearchParams {

	/**
	 * 是否允许查询总数.
	 *
	 * @return 允许查询总数
	 */
	@Nullable
	default Boolean allowSelectCount() {
		return null;
	}

}
