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

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;

/**
 * 地理坐标点接口.
 *
 * @author guer
 */
public interface IGeoPoint extends Serializable {

	/**
	 * wkb类型.
	 */
	int WKB_TYPE = 1;

	/**
	 * 获取空间引用标识符.
	 *
	 * @return 空间引用标识符
	 */
	@Nullable
	Integer getSrid();

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
	@JsonIgnore
	default boolean isEmpty() {
		return getLongitude() == null || getLatitude() == null;
	}
}
