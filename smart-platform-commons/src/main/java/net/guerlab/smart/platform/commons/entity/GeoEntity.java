package net.guerlab.smart.platform.commons.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 地理信息对象
 *
 * @author guer
 */
@Setter
@Getter
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
}
