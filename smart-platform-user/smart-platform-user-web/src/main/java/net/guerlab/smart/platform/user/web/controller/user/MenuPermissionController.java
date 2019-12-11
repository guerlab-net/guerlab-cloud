package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.smart.platform.user.core.searchparams.MenuPermissionSearchParams;
import net.guerlab.smart.platform.user.service.entity.MenuPermission;
import net.guerlab.smart.platform.user.service.service.MenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限
 *
 * @author guer
 */
@Api(tags = "菜单权限")
@RestController("/user/menuPermission")
@RequestMapping("/user/menuPermission")
public class MenuPermissionController {

    private MenuPermissionService service;

    @ApiOperation("查询")
    @GetMapping("/{permissionKey}")
    public List<Long> findList(@ApiParam(value = "权限关键字", required = true) @PathVariable String permissionKey) {
        MenuPermissionSearchParams searchParams = new MenuPermissionSearchParams();
        searchParams.setPermissionKey(permissionKey);

        return CollectionUtil.toList(service.findList(searchParams), MenuPermission::getMenuId);
    }

    @ApiOperation("保存")
    @PostMapping("/{permissionKey}")
    public void save(@ApiParam(value = "权限关键字", required = true) @PathVariable String permissionKey,
            @ApiParam(value = "菜单ID列表", required = true) @RequestBody List<Long> menuIds) {
        service.save(permissionKey, menuIds);
    }

    @Autowired
    public void setService(MenuPermissionService service) {
        this.service = service;
    }
}
