/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.geo.entity;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import net.guerlab.cloud.commons.entity.BaseEntity;

/**
 * 地理信息对象.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Setter
@Getter
@Schema(name = "GeoEntity", description = "地理信息对象")
public class GeoEntity extends BaseEntity implements IGeoEntity {

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

	/**
	 * 地理hash.
	 */
	@Schema(description = "地理hash")
	private String geoHash;
}
