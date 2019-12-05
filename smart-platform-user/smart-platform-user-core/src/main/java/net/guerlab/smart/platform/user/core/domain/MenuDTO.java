package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderTreeEntity;

/**
 * 菜单
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("菜单")
public class MenuDTO extends BaseOrderTreeEntity<MenuDTO> {

    /**
     * 默认组件路径
     */
    public static final String DEFAULT_COMPONENT_PATH = "Root";

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Long menuId;

    /**
     * 上级ID
     */
    @ApiModelProperty("上级ID")
    private Long parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 菜单显示名称
     */
    @ApiModelProperty("菜单显示名称")
    private String displayName;

    /**
     * 访问路径
     */
    @ApiModelProperty("访问路径")
    private String path;

    /**
     * 激活路径
     */
    @ApiModelProperty("激活路径")
    private String activePath;

    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    private String componentPath;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 图标类名
     */
    @ApiModelProperty("图标类名")
    private String iconCls;

    /**
     * 是否隐藏
     */
    @ApiModelProperty("是否隐藏")
    private Boolean hidden;

    /**
     * 是否展示叶节点
     */
    @ApiModelProperty("是否展示叶节点")
    private Boolean leaf;

    @Override
    public Long id() {
        return menuId;
    }

    @Override
    public Long parentId() {
        return parentId;
    }
}
