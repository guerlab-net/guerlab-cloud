package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.server.controller.BaseController;
import net.guerlab.smart.platform.user.core.domain.PermissionDTO;
import net.guerlab.smart.platform.user.core.exception.PermissionInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.PermissionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Permission;
import net.guerlab.smart.platform.user.service.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限
 *
 * @author guer
 */
@Api(tags = "权限")
@RestController("/user/permission")
@RequestMapping("/user/permission")
public class PermissionController
        extends BaseController<PermissionDTO, Permission, PermissionService, PermissionSearchParams, String> {

    @Override
    public void copyProperties(PermissionDTO dto, Permission entity, String id) {
        super.copyProperties(dto, entity, id);
        if (StringUtils.isNotBlank(id)) {
            entity.setPermissionKey(id);
        }
    }

    @Override
    protected ApplicationException nullPointException() {
        return new PermissionInvalidException();
    }

}
