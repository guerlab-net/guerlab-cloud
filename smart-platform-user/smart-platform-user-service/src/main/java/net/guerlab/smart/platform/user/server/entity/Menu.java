package net.guerlab.smart.platform.user.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;
import net.guerlab.smart.platform.user.core.domain.MenuDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_menu")
public class Menu extends BaseOrderEntity<Menu> implements DefaultConvertDTO<MenuDTO> {

    /**
     * 菜单ID
     */
    @Id
    private Long menuId;

    /**
     * 上级ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单显示名称
     */
    private String displayName;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 激活路径
     */
    private String activePath;

    /**
     * 组件路径
     */
    private String componentPath;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图标类名
     */
    private String iconCls;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 是否展示叶节点
     */
    private Boolean leaf;
}
