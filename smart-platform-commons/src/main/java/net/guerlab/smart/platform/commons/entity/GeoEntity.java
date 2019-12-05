package net.guerlab.smart.platform.commons.entity;

import io.swagger.annotations.ApiModelProperty;
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
public class GeoEntity extends BaseEntity implements IGeoEntity {

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private BigDecimal latitude;

    /**
     * 地理hash
     */
    @ApiModelProperty("地理hash")
    private String geoHash;
}
