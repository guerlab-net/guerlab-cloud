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
package net.guerlab.cloud.commons.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

/**
 * 地理信息对象
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Schema(name = "GeoEntity", description = "地理信息对象")
public class GeoEntity extends BaseEntity implements IGeoEntity {

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal latitude;

    /**
     * 地理hash
     */
    @Schema(description = "地理hash")
    private String geoHash;

    @Nullable
    @Override
    public BigDecimal getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(@Nullable BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Nullable
    @Override
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(@Nullable BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Nullable
    @Override
    public String getGeoHash() {
        return geoHash;
    }

    @Override
    public void setGeoHash(@Nullable String geoHash) {
        this.geoHash = geoHash;
    }
}
