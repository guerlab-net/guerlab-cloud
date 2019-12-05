package net.guerlab.smart.platform.commons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理hash
 *
 * @author guer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoHash {

    /**
     * 中央hash
     */
    private String center;

    /**
     * 中上hash
     */
    private String top;

    /**
     * 中下hash
     */
    private String bottom;

    /**
     * 中右hash
     */
    private String right;

    /**
     * 中左hash
     */
    private String left;

    /**
     * 上左hash
     */
    private String topLeft;

    /**
     * 上右hash
     */
    private String topRight;

    /**
     * 下右hash
     */
    private String bottomRight;

    /**
     * 下左hash
     */
    private String bottomLeft;
}
