package net.guerlab.smart.platform.user.core.searchparams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import javax.persistence.Column;
import java.util.Collection;

/**
 * 部门职务分配搜索参数
 *
 * @author guer
 */
@Setter
@Getter
public class DepartmentDutyDistributionSearchParams extends AbstractSearchParams {

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
