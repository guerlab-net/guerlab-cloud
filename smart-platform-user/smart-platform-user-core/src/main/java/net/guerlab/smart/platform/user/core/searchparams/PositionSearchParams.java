package net.guerlab.smart.platform.user.core.searchparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import javax.persistence.Column;
import java.util.Collection;

/**
 * 职位信息搜索参数
 *
 * @author guer
 */
@Setter
@Getter
@ApiModel("职位信息搜索参数")
public class PositionSearchParams extends AbstractSearchParams {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 用户ID列表
     */
    @ApiModelProperty("用户ID列表")
    @Column(name = "userId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> userIds;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 部门ID列表
     */
    @ApiModelProperty("部门ID列表")
    @Column(name = "departmentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> departmentIds;

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
}
