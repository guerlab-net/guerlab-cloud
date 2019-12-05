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
 * 菜单搜索参数
 *
 * @author guer
 */
@Setter
@Getter
@ApiModel("菜单搜索参数")
public class MenuSearchParams extends OrderSearchParams {

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

    /**
     * 上级ID
     */
    @ApiModelProperty("上级ID")
    private Long parentId;

    /**
     * 是否隐藏
     */
    @ApiModelProperty("是否隐藏")
    private Boolean hidden;
}
