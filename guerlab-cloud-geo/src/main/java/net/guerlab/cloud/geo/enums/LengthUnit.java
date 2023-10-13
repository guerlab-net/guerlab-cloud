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

package net.guerlab.cloud.geo.enums;

import java.math.BigDecimal;

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
	KILOMETER(new BigDecimal("0.001")),

	/**
	 * 米.
	 */
	METRE(BigDecimal.ONE),

	/**
	 * 厘米.
	 */
	CENTIMETER(new BigDecimal("100"));

	/**
	 * 基于千米的比例.
	 */
	private final BigDecimal ratio;

	LengthUnit(BigDecimal ratio) {
		this.ratio = ratio;
	}
}
