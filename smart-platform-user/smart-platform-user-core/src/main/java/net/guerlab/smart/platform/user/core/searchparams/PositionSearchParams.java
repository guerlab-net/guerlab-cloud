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
 * 职位搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("职位搜索参数")
public class PositionSearchParams extends OrderSearchParams {

    /**
     * 职位ID
     */
    @ApiModelProperty("职位ID")
    private Long positionId;

    /**
     * 职位ID列表
     */
    @ApiModelProperty("职位ID列表")
    @Column(name = "positionId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> positionIds;

    /**
     * 职位名称
     */
    @ApiModelProperty("职位名称")
    private String positionName;

    /**
     * 职位名称
     */
    @ApiModelProperty("职位名称")
    @Column(name = "positionName")
    @SearchModel(SearchModelType.LIKE)
    private String positionNameLike;
}
