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
import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.commons.entity.IGeoEntity;
import net.guerlab.cloud.commons.exception.GeoEntityInvalidException;
import net.guerlab.cloud.commons.exception.LatitudeInvalidException;
import net.guerlab.cloud.commons.exception.LongitudeInvalidException;
import net.guerlab.cloud.geo.domain.GeoHash;
import net.guerlab.cloud.geo.searchparams.GeoSearchParams;
import net.guerlab.cloud.searchparams.SearchParamsUtils;

/**
 * 地理信息实体工具类.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class GeoEntityUtils {

	private GeoEntityUtils() {

	}

	/**
	 * 属性值检查.
	 *
	 * @param entity 地理信息实体
	 */
	public static void propertiesCheck(@Nullable IGeoEntity entity) {
		if (entity == null) {
			throw new GeoEntityInvalidException();
		}
		if (entity.getLongitude() == null) {
			throw new LongitudeInvalidException();
		}
		if (entity.getLatitude() == null) {
			throw new LatitudeInvalidException();
		}
	}

	/**
	 * 设置地理信息.
	 *
	 * @param entity 地理信息实体
	 */
	public static void setGeoInfo(@Nullable IGeoEntity entity) {
		if (entity == null) {
			return;
		}

		BigDecimal longitude = entity.getLongitude();
		BigDecimal latitude = entity.getLatitude();

		if (longitude == null || latitude == null) {
			entity.setLongitude(null);
			entity.setLatitude(null);
			entity.setGeoHash(null);
		}
		else {
			entity.setGeoHash(GeoHashUtils.encode(longitude, latitude));
		}
	}

	/**
	 * 解析搜索参数为Map结构.
	 *
	 * @param searchParams 地理信息实体搜索参数
	 * @return 参数Map
	 */
	public static Map<String, Object> parseSearchParams(GeoSearchParams searchParams) {
		Map<String, Object> params = new HashMap<>(8);
		SearchParamsUtils.handler(searchParams, params);

		setGeoHashInfo(params, searchParams);

		return params;
	}

	private static void setGeoHashInfo(Map<String, Object> params, GeoSearchParams searchParams) {
		BigDecimal longitude = getVal(searchParams.getViewLongitude(), searchParams.getLongitude());
		BigDecimal latitude = getVal(searchParams.getViewLatitude(), searchParams.getLatitude());

		if (longitude == null || latitude == null) {
			return;
		}

		String geoHash = GeoHashUtils.encode(longitude, latitude);

		GeoHash geoHashInfo = GeoHashUtils.getGeoHashExpand(geoHash, 6);

		params.put("geoHashInfo", geoHashInfo);
	}

	@Nullable
	private static BigDecimal getVal(@Nullable BigDecimal v1, @Nullable BigDecimal v2) {
		return v1 == null ? v2 : v1;
	}
}
