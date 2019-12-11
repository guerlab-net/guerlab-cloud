package net.guerlab.smart.platform.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.guerlab.smart.platform.user.core.domain.MenuPermissionDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单权限
 *
 * @author guer
 */
@Data
@Table(name = "user_menu_permission")
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermission implements DefaultConvertDTO<MenuPermissionDTO> {

    /**
     * 权限关键字
     */
    @Id
    private String permissionKey;

    /**
     * 菜单ID
     */
    @Id
    private Long menuId;
}
