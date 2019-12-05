package net.guerlab.smart.platform.commons.entity;

import java.math.BigDecimal;

/**
 * 地理信息对象接口
 *
 * @author guer
 */
public interface IGeoEntity {

    /**
     * 获取经度
     *
     * @return 经度
     */
    BigDecimal getLongitude();

    /**
     * 设置经度
     *
     * @param longitude
     *         经度
     */
    void setLongitude(BigDecimal longitude);

    /**
     * 获取纬度
     *
     * @return 纬度
     */
    BigDecimal getLatitude();

    /**
     * 设置纬度
     *
     * @param latitude
     *         纬度
     */
    void setLatitude(BigDecimal latitude);

    /**
     * 获取地理hash
     *
     * @return 地理hash
     */
    String getGeoHash();

    /**
     * 设置地理hash
     *
     * @param geoHash
     *         地理hash
     */
    void setGeoHash(String geoHash);
}
