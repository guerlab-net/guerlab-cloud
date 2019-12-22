package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.exception.PermissionKeyInvalidException;
import net.guerlab.smart.platform.user.core.exception.PermissionKeyRepeatException;
import net.guerlab.smart.platform.user.core.exception.PermissionNameInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DutyPermissionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.MenuPermissionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Permission;
import net.guerlab.smart.platform.user.service.mapper.PermissionMapper;
import net.guerlab.smart.platform.user.service.service.DutyPermissionService;
import net.guerlab.smart.platform.user.service.service.MenuPermissionService;
import net.guerlab.smart.platform.user.service.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限服务实现
 *
 * @author guer
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, String, PermissionMapper>
        implements PermissionService {

    private DutyPermissionService dutyPermissionService;

    private MenuPermissionService menuPermissionService;

    @Override
    protected void insertBefore(Permission entity) {
        String key = StringUtils.trimToNull(entity.getPermissionKey());
        String permissionName = StringUtils.trimToNull(entity.getPermissionName());

        if (key == null) {
            throw new PermissionKeyInvalidException();
        }
        if (permissionName == null) {
            throw new PermissionNameInvalidException();
        }
        if (selectById(key) != null) {
            throw new PermissionKeyRepeatException();
        }

        entity.setPermissionKey(key);
        entity.setPermissionName(permissionName);
    }

    @Override
    protected void deleteAfter(Permission entity, Boolean force) {
        deleteByIdAfter(entity.getPermissionKey(), force);
    }

    @Override
    protected void deleteByIdAfter(String permissionKey, Boolean force) {
        String key = StringUtils.trimToNull(permissionKey);

        if (key == null) {
            return;
        }

        clearDutyPermission(key);
        clearMenuPermission(key);
    }

    private void clearDutyPermission(String key) {
        DutyPermissionSearchParams searchParams = new DutyPermissionSearchParams();
        searchParams.setPermissionKey(key);

        dutyPermissionService.delete(searchParams);
    }

    private void clearMenuPermission(String key) {
        MenuPermissionSearchParams searchParams = new MenuPermissionSearchParams();
        searchParams.setPermissionKey(key);

        menuPermissionService.delete(searchParams);
    }

    @Autowired
    public void setDutyPermissionService(DutyPermissionService dutyPermissionService) {
        this.dutyPermissionService = dutyPermissionService;
    }

    @Autowired
    public void setMenuPermissionService(MenuPermissionService menuPermissionService) {
        this.menuPermissionService = menuPermissionService;
    }
}
