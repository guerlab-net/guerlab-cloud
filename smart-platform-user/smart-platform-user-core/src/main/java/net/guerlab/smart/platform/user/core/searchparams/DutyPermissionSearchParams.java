package net.guerlab.smart.platform.user.core.searchparams;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * 职务权限搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("职务权限搜索参数")
public class DutyPermissionSearchParams extends AbstractSearchParams {

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

    /**
     * 权限关键字
     */
    @ApiModelProperty("权限关键字")
    private String permissionKey;

    /**
     * 权限关键字列表
     */
    @ApiModelProperty("权限关键字列表")
    @Column(name = "permissionKey")
    @SearchModel(SearchModelType.IN)
    private Collection<String> permissionKeys;

    /**
     * 部门职务
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String departmentDuty;

    /**
     * 部门职务列表
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Column(name = "departmentDuty")
    @SearchModel(SearchModelType.IN)
    private Collection<String> departmentDutys;
}
