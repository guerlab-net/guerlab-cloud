package net.guerlab.smart.platform.user.core.searchparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.smart.platform.commons.searchparams.OrderSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import javax.persistence.Column;
import java.util.Collection;

/**
 * 职务搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("职务搜索参数")
public class DutySearchParams extends OrderSearchParams {

    /**
     * 职务ID
     */
    @ApiModelProperty("职务ID")
    private Long dutyId;

    /**
     * 职务ID列表
     */
    @ApiModelProperty("职务ID列表")
    @Column(name = "dutyId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> dutyIds;

    /**
     * 职务名称
     */
    @ApiModelProperty("职务名称")
    private String dutyName;

    /**
     * 职务名称
     */
    @ApiModelProperty("职务名称")
    @Column(name = "dutyName")
    @SearchModel(SearchModelType.LIKE)
    private String dutyNameLike;
}
