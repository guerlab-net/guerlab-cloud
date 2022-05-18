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

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.lang.Nullable;

/**
 * 地理坐标点接口.
 *
 * @author guer
 */
public interface IGeoPoint extends Serializable {

	/**
	 * 获取经度.
	 *
	 * @return 经度
	 */
	@Nullable
	BigDecimal getLongitude();

	/**
	 * 获取纬度.
	 *
	 * @return 纬度
	 */
	@Nullable
	BigDecimal getLatitude();

	/**
	 * 判断是否为空.
	 *
	 * @return 是否为空
	 */
	default boolean isEmpty() {
		return getLongitude() == null || getLatitude() == null;
	}
}
