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
 * 职位权限搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("职位权限搜索参数")
public class PositionPermissionSearchParams extends AbstractSearchParams {

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
     * 部门职位
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String departmentPosition;

    /**
     * 部门职位列表
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Column(name = "departmentPosition")
    @SearchModel(SearchModelType.IN)
    private Collection<String> departmentPositions;
}
