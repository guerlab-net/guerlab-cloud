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

import lombok.Data;

/**
 * 地理hash.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Data
public class GeoHash {

	/**
	 * 中央hash.
	 */
	private String center;

	/**
	 * 中上hash.
	 */
	private String top;

	/**
	 * 中下hash.
	 */
	private String bottom;

	/**
	 * 中右hash.
	 */
	private String right;

	/**
	 * 中左hash.
	 */
	private String left;

	/**
	 * 上左hash.
	 */
	private String topLeft;

	/**
	 * 上右hash.
	 */
	private String topRight;

	/**
	 * 下右hash.
	 */
	private String bottomRight;

	/**
	 * 下左hash.
	 */
	private String bottomLeft;

	/**
	 * 创建地理hash.
	 */
	public GeoHash() {
	}

	/**
	 * 创建地理hash.
	 *
	 * @param center      中央hash
	 * @param top         顶部hash
	 * @param bottom      底部hash
	 * @param right       右侧hash
	 * @param left        左侧hash
	 * @param topLeft     左上hash
	 * @param topRight    右上hash
	 * @param bottomRight 右下hash
	 * @param bottomLeft  左下hash
	 */
	public GeoHash(String center, String top, String bottom, String right, String left, String topLeft, String topRight,
			String bottomRight, String bottomLeft) {
		this.center = center;
		this.top = top;
		this.bottom = bottom;
		this.right = right;
		this.left = left;
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomRight = bottomRight;
		this.bottomLeft = bottomLeft;
	}
}
