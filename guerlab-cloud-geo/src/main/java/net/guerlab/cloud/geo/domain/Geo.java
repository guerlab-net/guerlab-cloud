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

package net.guerlab.cloud.geo.domain;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 地理位置对象.
 *
 * @author guer
 */
@Data
public class Geo {

	/**
	 * 经度.
	 */
	private BigDecimal longitude;

	/**
	 * 纬度.
	 */
	private BigDecimal latitude;

	/**
	 * 创建地理位置对象.
	 */
	public Geo() {
	}

	/**
	 * 创建地理位置对象.
	 *
	 * @param longitude 经度
	 * @param latitude  纬度
	 */
	public Geo(BigDecimal longitude, BigDecimal latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
