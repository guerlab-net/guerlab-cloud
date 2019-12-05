package net.guerlab.smart.platform.commons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 经纬度
 *
 * @author guer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}
