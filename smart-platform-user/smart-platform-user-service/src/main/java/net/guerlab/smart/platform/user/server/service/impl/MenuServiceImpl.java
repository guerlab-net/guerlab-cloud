package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.commons.util.OrderEntityUtils;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.domain.MenuDTO;
import net.guerlab.smart.platform.user.core.exception.MenuNameInvalidException;
import net.guerlab.smart.platform.user.core.exception.MenuPathInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.MenuPermissionSearchParams;
import net.guerlab.smart.platform.user.server.entity.Menu;
import net.guerlab.smart.platform.user.server.mapper.MenuMapper;
import net.guerlab.smart.platform.user.server.service.MenuPermissionService;
import net.guerlab.smart.platform.user.server.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 菜单服务实现
 *
 * @author guer
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long, MenuMapper> implements MenuService {

    private MenuPermissionService menuPermissionService;

    @Override
    protected void insertBefore(Menu entity) {
        String name = StringUtils.trimToNull(entity.getName());
        String path = StringUtils.trimToNull(entity.getPath());
        String componentPath = StringUtils.trimToNull(entity.getComponentPath());

        if (name == null) {
            throw new MenuNameInvalidException();
        }
        if (path == null) {
            throw new MenuPathInvalidException();
        }

        entity.setMenuId(sequence.nextId());
        entity.setName(name);
        entity.setPath(path);
        entity.setComponentPath(Optional.ofNullable(componentPath).orElse(MenuDTO.DEFAULT_COMPONENT_PATH));
        if (!NumberHelper.greaterZero(entity.getParentId())) {
            entity.setParentId(Constants.DEFAULT_PARENT_ID);
        }
        if (entity.getHidden() == null) {
            entity.setHidden(false);
        }
        if (entity.getLeaf() == null) {
            entity.setLeaf(false);
        }
        OrderEntityUtils.propertiesCheck(entity);
    }

    @Override
    protected void updateAfter(Menu entity) {
        clearMenuPermission(entity.getMenuId());
    }

    @Override
    protected void deleteByIdAfter(Long menuId, Boolean force) {
        clearMenuPermission(menuId);
    }

    private void clearMenuPermission(Long menuId) {
        if (!NumberHelper.greaterZero(menuId)) {
            return;
        }

        MenuPermissionSearchParams searchParams = new MenuPermissionSearchParams();
        searchParams.setMenuId(menuId);

        menuPermissionService.delete(searchParams);
    }

    @Autowired
    public void setMenuPermissionService(MenuPermissionService menuPermissionService) {
        this.menuPermissionService = menuPermissionService;
    }
}
