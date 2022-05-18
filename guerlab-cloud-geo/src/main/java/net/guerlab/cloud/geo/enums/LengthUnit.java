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

package net.guerlab.cloud.geo.enums;

import lombok.Getter;

/**
 * 长度单位.
 *
 * @author guer
 */
@Getter
public enum LengthUnit {

	/**
	 * 千米.
	 */
	KILOMETER(1),

	/**
	 * 米.
	 */
	METRE(1000),

	/**
	 * 厘米.
	 */
	CENTIMETER(1000 * 100);

	/**
	 * 基于千米的比例.
	 */
	private final long ratio;

	LengthUnit(long ratio) {
		this.ratio = ratio;
	}
}
