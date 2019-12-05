package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.server.service.BaseBatchServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.MenuPermissionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.MenuSearchParams;
import net.guerlab.smart.platform.user.server.entity.Menu;
import net.guerlab.smart.platform.user.server.entity.MenuPermission;
import net.guerlab.smart.platform.user.server.mapper.MenuPermissionMapper;
import net.guerlab.smart.platform.user.server.service.MenuPermissionService;
import net.guerlab.smart.platform.user.server.service.MenuService;
import net.guerlab.smart.platform.user.server.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限服务实现
 *
 * @author guer
 */
@Service
public class MenuPermissionServiceImpl extends BaseBatchServiceImpl<MenuPermission, String, MenuPermissionMapper>
        implements MenuPermissionService {

    private PermissionService permissionService;

    private MenuService menuService;

    @Override
    public Class<MenuPermission> getEntityClass() {
        return MenuPermission.class;
    }

    @Override
    protected MenuPermission batchSaveBefore(MenuPermission entity) {
        return entity != null && StringUtils.isNotBlank(entity.getPermissionKey()) && NumberHelper
                .greaterZero(entity.getMenuId()) ? entity : null;
    }

    @Override
    public List<MenuPermission> findList(MenuPermissionSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public void save(String permissionKey, Collection<Long> menuIds) {
        String key = StringUtils.trimToNull(permissionKey);

        if (key == null || CollectionUtil.isBlank(menuIds) || permissionService.selectById(key) == null) {
            return;
        }

        delete(key);

        Collection<Long> filledMenuIds = getFilledMenuIds(menuIds);

        if (CollectionUtil.isBlank(filledMenuIds)) {
            return;
        }

        List<MenuPermission> saves = filledMenuIds.stream().map(menuId -> new MenuPermission(key, menuId))
                .collect(Collectors.toList());

        mapper.replaceInsertList(saves);
    }

    private Collection<Long> getFilledMenuIds(Collection<Long> menuIds) {
        MenuSearchParams searchParams = new MenuSearchParams();
        searchParams.setMenuIds(menuIds);

        return CollectionUtil.toSet(menuService.selectAll(searchParams), Menu::getMenuId);
    }

    @Override
    public void delete(MenuPermissionSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
