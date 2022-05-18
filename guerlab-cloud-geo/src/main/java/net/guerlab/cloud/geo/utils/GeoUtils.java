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

package net.guerlab.cloud.geo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import net.guerlab.cloud.geo.domain.IGeoPoint;
import net.guerlab.cloud.geo.enums.LengthUnit;

/**
 * 地理信息工具类.
 *
 * @author guer
 */
public final class GeoUtils {

	/**
	 * 默认精度.
	 */
	public static final int DEFAULT_SCALE = 2;

	/**
	 * 默认地球半径.
	 */
	private static final double EARTH_RADIUS = 6371000;

	private GeoUtils() {
	}

	/**
	 * 计算两点之间距离，单位：km，结果保留2位小数四舍五入.
	 *
	 * @param from 起点
	 * @param to   终点
	 * @return 距离
	 */
	public static BigDecimal distance(IGeoPoint from, IGeoPoint to) {
		return distance(from, to, LengthUnit.KILOMETER);
	}

	/**
	 * 计算两点之间距离，结果保留2位小数四舍五入.
	 *
	 * @param from 起点
	 * @param to   终点
	 * @param unit 单位
	 * @return 距离
	 */
	public static BigDecimal distance(IGeoPoint from, IGeoPoint to, LengthUnit unit) {
		return distance(from, to, unit, DEFAULT_SCALE);
	}

	/**
	 * 计算两点之间距离，结果四舍五入.
	 *
	 * @param from  起点
	 * @param to    终点
	 * @param unit  单位
	 * @param scale 精度
	 * @return 距离
	 */
	public static BigDecimal distance(IGeoPoint from, IGeoPoint to, LengthUnit unit, int scale) {
		return distance(from, to, unit, scale, RoundingMode.HALF_UP);
	}

	/**
	 * 计算两点之间距离.
	 *
	 * @param from         起点
	 * @param to           终点
	 * @param unit         单位
	 * @param scale        精度
	 * @param roundingMode 舍入处理方式
	 * @return 距离
	 */
	public static BigDecimal distance(IGeoPoint from, IGeoPoint to, LengthUnit unit, int scale, RoundingMode roundingMode) {
		double radiansFromX = Math.toRadians(Objects.requireNonNull(from.getLongitude()).doubleValue());
		double radiansFromY = Math.toRadians(Objects.requireNonNull(from.getLatitude()).doubleValue());
		double radiansToX = Math.toRadians(Objects.requireNonNull(to.getLongitude()).doubleValue());
		double radiansToY = Math.toRadians(Objects.requireNonNull(to.getLatitude()).doubleValue());

		double cos = Math.cos(radiansFromY) * Math.cos(radiansToY) * Math.cos(radiansFromX - radiansToX)
				+ Math.sin(radiansFromY) * Math.sin(radiansToY);
		double acos = Math.acos(cos);
		return BigDecimal.valueOf(EARTH_RADIUS * acos).multiply(new BigDecimal(unit.getRatio()))
				.setScale(scale, roundingMode);
	}
}
