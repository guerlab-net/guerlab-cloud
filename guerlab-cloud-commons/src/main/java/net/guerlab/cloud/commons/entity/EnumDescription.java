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

package net.guerlab.cloud.commons.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 枚举说明.
 *
 * @author guer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "EnumDescription", description = "枚举说明")
public class EnumDescription {

	/**
	 * key.
	 */
	@Schema(description = "key")
	private String key;

	/**
	 * 说明.
	 */
	@Schema(description = "说明")
	private String description;
}
