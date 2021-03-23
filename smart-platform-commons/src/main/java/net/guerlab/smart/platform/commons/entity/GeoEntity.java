package net.guerlab.smart.platform.commons.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class GeoEntity extends BaseEntity implements IGeoEntity {

    /**
     * 经度
     */
    @Schema(name = "经度")
    @TableField(value = "LONGITUDE")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Schema(name = "纬度")
    @TableField(value = "LATITUDE")
    private BigDecimal latitude;

    /**
     * 地理hash
     */
    @Schema(name = "地理hash")
    @TableField(value = "GEO_HASH")
    private String geoHash;
}
