package net.guerlab.smart.platform.commons.searchparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("地理信息实体搜索参数")
public class GeoSearchParams extends AbstractSearchParams {

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
     * 视图经度
     */
    @ApiModelProperty("视图经度")
    private BigDecimal viewLongitude;

    /**
     * 视图纬度
     */
    @ApiModelProperty("视图纬度")
    private BigDecimal viewLatitude;

    /**
     * 地址位置hash信息
     */
    @ApiModelProperty(hidden = true)
    @SearchModel(SearchModelType.IGNORE)
    private GeoHash geoHashInfo;
}
