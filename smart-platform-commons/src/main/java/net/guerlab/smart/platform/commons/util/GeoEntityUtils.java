package net.guerlab.smart.platform.commons.util;

import net.guerlab.smart.platform.commons.domain.GeoHash;
import net.guerlab.smart.platform.commons.entity.IGeoEntity;
import net.guerlab.smart.platform.commons.exception.GeoEntityInvalidException;
import net.guerlab.smart.platform.commons.exception.LatitudeInvalidException;
import net.guerlab.smart.platform.commons.exception.LongitudeInvalidException;
import net.guerlab.smart.platform.commons.searchparams.GeoSearchParams;
import net.guerlab.spring.searchparams.SearchParamsUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 地理信息实体工具类
 *
 * @author guer
 */
public class GeoEntityUtils {

    private GeoEntityUtils() {

    }

    /**
     * 属性值检查
     *
     * @param entity
     *         地理信息实体
     */
    public static void propertiesCheck(IGeoEntity entity) {
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
     * 设置地理信息
     *
     * @param entity
     *         地理信息实体
     */
    public static void setGeoInfo(IGeoEntity entity) {
        if (entity == null) {
            return;
        }

        BigDecimal longitude = entity.getLongitude();
        BigDecimal latitude = entity.getLatitude();

        if (longitude == null || latitude == null) {
            entity.setLongitude(null);
            entity.setLatitude(null);
            entity.setGeoHash(null);
        } else {
            entity.setGeoHash(GeoHashUtils.encode(longitude, latitude));
        }
    }

    /**
     * 解析搜索参数为Map结构
     *
     * @param searchParams
     *         地理信息实体搜索参数
     * @return 参数Map
     */
    public static Map<String, Object> parseSearchParams(GeoSearchParams searchParams) {
        Map<String, Object> params = new HashMap<>(8);
        SearchParamsUtils.handler(searchParams, params);

        int pageId = Math.max(searchParams.getPageId(), 1);
        int pageSize = searchParams.getPageSize();

        setGeoHashInfo(params, searchParams);

        return params;
    }

    private static void setGeoHashInfo(Map<String, Object> params, GeoSearchParams searchParams) {
        BigDecimal longitude = searchParams.getLongitude();
        BigDecimal latitude = searchParams.getLatitude();
        BigDecimal viewLongitude = searchParams.getViewLongitude();
        BigDecimal viewLatitude = searchParams.getViewLatitude();

        if (viewLongitude == null) {
            viewLongitude = longitude;
        }
        if (viewLatitude == null) {
            viewLatitude = latitude;
        }

        if (viewLongitude == null || viewLatitude == null) {
            return;
        }

        String geoHash = GeoHashUtils.encode(viewLongitude, viewLatitude);

        GeoHash geoHashInfo = GeoHashUtils.getGeoHashExpand(geoHash, 6);

        params.put("geoHashInfo", geoHashInfo);
    }
}
