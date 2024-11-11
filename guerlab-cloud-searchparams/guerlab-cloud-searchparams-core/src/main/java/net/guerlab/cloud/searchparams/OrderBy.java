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

package net.guerlab.cloud.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 排序字段.
 *
 * @author guer
 */
@Data
@Accessors(chain = true)
@Builder
@Schema(description = "排序字段")
public class OrderBy {

	/**
	 * 字段名.
	 */
	@Schema(description = "字段名")
	private String columnName;

	/**
	 * 是否升序.
	 */
	@Schema(description = "是否升序", defaultValue = "true")
	@Builder.Default
	private boolean asc = true;

	@SuppressWarnings("unused")
	public OrderBy() {
		this(null, true);
	}

	public OrderBy(String columnName) {
		this(columnName, true);
	}

	public OrderBy(String columnName, boolean asc) {
		this.columnName = columnName;
		this.asc = asc;
	}
}
