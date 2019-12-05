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
 * 任职信息搜索参数
 *
 * @author guer
 */
@Setter
@Getter
@ApiModel("任职信息搜索参数")
public class TakeOfficeSearchParams extends AbstractSearchParams {

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
}
