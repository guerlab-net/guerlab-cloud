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
 * 菜单权限搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("菜单权限搜索参数")
public class MenuPermissionSearchParams extends AbstractSearchParams {

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
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Long menuId;

    /**
     * 菜单ID列表
     */
    @ApiModelProperty("菜单ID列表")
    @Column(name = "menuId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> menuIds;
}
