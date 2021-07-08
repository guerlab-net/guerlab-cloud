/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.domain;

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
