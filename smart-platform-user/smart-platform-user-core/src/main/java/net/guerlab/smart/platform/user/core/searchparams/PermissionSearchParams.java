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
 * 权限查询参数
 *
 * @author guer
 */
@Setter
@Getter
@ApiModel("权限查询参数")
public class PermissionSearchParams extends AbstractSearchParams {

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
     * 权限名称关键字
     */
    @ApiModelProperty("权限名称关键字")
    @SearchModel(SearchModelType.LIKE)
    private String permissionName;
}
