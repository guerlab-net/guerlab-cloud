/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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
import java.util.Arrays;
import java.util.Objects;

import net.guerlab.cloud.geo.domain.GeoPoint;
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
	public static final double EARTH_RADIUS = 6371000;

	/**
	 * 大端序标识.
	 */
	public static final byte BIG_ENDIAN_BYTE = 0x00;

	/**
	 * 小端序标识.
	 */
	public static final byte LITTLE_ENDIAN_BYTE = 0x01;

	/**
	 * 默认空间引用标识符.
	 */
	public static final int DEFAULT_SRID_BYTES = 0;

	private GeoUtils() {
	}

	/**
	 * 计算两点之间距离，单位：米，结果保留2位小数四舍五入.
	 *
	 * @param from 起点
	 * @param to   终点
	 * @return 距离
	 */
	public static BigDecimal distance(IGeoPoint from, IGeoPoint to) {
		return distance(from, to, LengthUnit.METRE);
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
		return BigDecimal.valueOf(EARTH_RADIUS * acos).multiply(unit.getRatio())
				.setScale(scale, roundingMode);
	}


	/**
	 * 将bytes数组解析为地理坐标点.
	 *
	 * @param bytes byte数组
	 * @return 地理坐标点
	 */
	public static GeoPoint toGeoPoint(byte[] bytes) {
		boolean bigEndian = (bytes[4] == BIG_ENDIAN_BYTE);
		byte[] wkbTypeBytes = Arrays.copyOfRange(bytes, 5, 9);
		int wkbType = bytesToInt(wkbTypeBytes, bigEndian);
		if (wkbType != IGeoPoint.WKB_TYPE) {
			throw new IllegalArgumentException("wkbType is not Point");
		}

		byte[] sridBytes = Arrays.copyOfRange(bytes, 0, 4);
		byte[] longitudeBytes = Arrays.copyOfRange(bytes, 9, 17);
		byte[] latitudeBytes = Arrays.copyOfRange(bytes, 17, 25);

		BigDecimal longitude = BigDecimal.valueOf(Double.longBitsToDouble(bytesToLong(longitudeBytes, bigEndian)));
		BigDecimal latitude = BigDecimal.valueOf(Double.longBitsToDouble(bytesToLong(latitudeBytes, bigEndian)));

		GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(latitude);
		geoPoint.setLongitude(longitude);
		geoPoint.setSrid(bytesToInt(sridBytes, bigEndian));
		return geoPoint;
	}

	/**
	 * 使用小端序将地理坐标点解析为byte数组.
	 *
	 * @param geoPoint 地理坐标点
	 * @return byte数组
	 */
	public static byte[] toBytes(GeoPoint geoPoint) {
		return toBytes(geoPoint, false);
	}

	/**
	 * 将地理坐标点解析为byte数组.
	 *
	 * @param geoPoint  地理坐标点
	 * @param bigEndian 是否采用大端序
	 * @return byte数组
	 */
	public static byte[] toBytes(GeoPoint geoPoint, boolean bigEndian) {
		double longitude = Objects.requireNonNull(geoPoint.getLongitude()).doubleValue();
		double latitude = Objects.requireNonNull(geoPoint.getLatitude()).doubleValue();

		int srid = geoPoint.getSrid() != null ? geoPoint.getSrid() : DEFAULT_SRID_BYTES;

		byte[] bytes = new byte[25];
		System.arraycopy(intToBytes(srid, bigEndian), 0, bytes, 0, 4);
		bytes[4] = bigEndian ? BIG_ENDIAN_BYTE : LITTLE_ENDIAN_BYTE;
		System.arraycopy(intToBytes(IGeoPoint.WKB_TYPE, bigEndian), 0, bytes, 5, 4);
		System.arraycopy(longToBytes(Double.doubleToLongBits(longitude), bigEndian), 0, bytes, 9, 8);
		System.arraycopy(longToBytes(Double.doubleToLongBits(latitude), bigEndian), 0, bytes, 17, 8);

		return bytes;
	}

	private static int bytesToInt(byte[] bytes, boolean bigEndian) {
		int result = 0;
		if (bigEndian) {
			for (int i = 0; i < 4; i++) {
				result = (result << 4) + (bytes[i] & 0xff);
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				result += (bytes[i] & 0xff) << (8 * i);
			}
		}
		return result;
	}

	private static byte[] intToBytes(int val, boolean bigEndian) {
		byte[] bytes = new byte[4];
		if (bigEndian) {
			for (int i = 0; i < 4; i++) {
				bytes[3 - i] = (byte) (val >> (8 * i) & 0xff);
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				bytes[i] = (byte) (val >> (8 * i) & 0xff);
			}
		}
		return bytes;
	}

	private static long bytesToLong(byte[] bytes, boolean bigEndian) {
		long result = 0;
		if (bigEndian) {
			for (int i = 0; i < 8; i++) {
				result = (result << 8) + (bytes[i] & 0xff);
			}
		}
		else {
			for (int i = 0; i < 8; i++) {
				result += ((long) (bytes[i] & 0xff)) << (8 * i);
			}
		}
		return result;
	}

	private static byte[] longToBytes(long val, boolean bigEndian) {
		byte[] bytes = new byte[8];
		if (bigEndian) {
			for (int i = 0; i < 8; i++) {
				bytes[7 - i] = (byte) (val >> (8 * i) & 0xff);
			}
		}
		else {
			for (int i = 0; i < 8; i++) {
				bytes[i] = (byte) (val >> (8 * i) & 0xff);
			}
		}
		return bytes;
	}
}
