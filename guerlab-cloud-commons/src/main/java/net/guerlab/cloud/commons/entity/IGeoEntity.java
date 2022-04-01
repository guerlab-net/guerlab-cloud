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

package net.guerlab.cloud.commons.entity;

import java.math.BigDecimal;

import org.springframework.lang.Nullable;

/**
 * 地理信息对象接口.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public interface IGeoEntity {

	/**
	 * 获取经度.
	 *
	 * @return 经度
	 */
	@Nullable
	BigDecimal getLongitude();

	/**
	 * 设置经度.
	 *
	 * @param longitude 经度
	 */
	void setLongitude(@Nullable BigDecimal longitude);

	/**
	 * 获取纬度.
	 *
	 * @return 纬度
	 */
	@Nullable
	BigDecimal getLatitude();

	/**
	 * 设置纬度.
	 *
	 * @param latitude 纬度
	 */
	void setLatitude(@Nullable BigDecimal latitude);

	/**
	 * 获取地理hash.
	 *
	 * @return 地理hash
	 */
	@Nullable
	String getGeoHash();

	/**
	 * 设置地理hash.
	 *
	 * @param geoHash 地理hash
	 */
	void setGeoHash(@Nullable String geoHash);
}
