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

package net.guerlab.cloud.geo.domain;

import java.io.Serial;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * 地理坐标点.
 *
 * @author guer
 */
@Data
@Schema(name = "GeoPoint", description = "地理坐标点")
public class GeoPoint implements IGeoPoint {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 空间引用标识符.
	 */
	@Nullable
	@Schema(description = "空间引用标识符")
	private Integer srid;

	/**
	 * 经度.
	 */
	@Schema(description = "经度")
	private BigDecimal longitude;

	/**
	 * 纬度.
	 */
	@Schema(description = "纬度")
	private BigDecimal latitude;
}
