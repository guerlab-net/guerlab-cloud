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
package net.guerlab.smart.platform.commons.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.smart.platform.commons.domain.GeoHash;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import java.math.BigDecimal;

/**
 * 地理信息实体搜索参数
 *
 * @author guer
 */
@Setter
@Getter
@Schema(name = "GeoSearchParams", description = "地理信息实体搜索参数")
public class GeoSearchParams extends AbstractSearchParams {

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
     * 视图经度
     */
    @Schema(description = "视图经度")
    private BigDecimal viewLongitude;

    /**
     * 视图纬度
     */
    @Schema(description = "视图纬度")
    private BigDecimal viewLatitude;

    /**
     * 地址位置hash信息
     */
    @Schema(hidden = true)
    @SearchModel(SearchModelType.IGNORE)
    private GeoHash geoHashInfo;
}
