package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.TreeUtils;
import net.guerlab.smart.platform.server.controller.BaseController;
import net.guerlab.smart.platform.user.auth.annotation.NeedTwoFactorAuthentication;
import net.guerlab.smart.platform.user.core.domain.MenuDTO;
import net.guerlab.smart.platform.user.core.exception.HasSubMenuException;
import net.guerlab.smart.platform.user.core.searchparams.MenuSearchParams;
import net.guerlab.smart.platform.user.service.entity.Menu;
import net.guerlab.smart.platform.user.service.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 菜单
 *
 * @author guer
 */
@Api(tags = "菜单")
@RestController("/user/menu")
@RequestMapping("/user/menu")
public class MenuController extends BaseController<MenuDTO, Menu, MenuService, MenuSearchParams, Long> {

    @Override
    public void copyProperties(MenuDTO dto, Menu entity, Long id) {
        super.copyProperties(dto, entity, id);
        entity.setMenuId(id);
    }

    @NeedTwoFactorAuthentication
    @Override
    public MenuDTO save(@ApiParam(value = "对象数据", required = true) @RequestBody MenuDTO dto) {
        return super.save(dto);
    }

    @NeedTwoFactorAuthentication
    @Override
    public MenuDTO update(@ApiParam(value = "id", required = true) @PathVariable Long id,
            @ApiParam(value = "对象数据", required = true) @RequestBody MenuDTO dto) {
        return super.update(id, dto);
    }

    @NeedTwoFactorAuthentication
    @Override
    public void delete(@ApiParam(value = "菜单ID", required = true) @PathVariable Long id,
            @ApiParam(value = "强制删除标志") @RequestParam(required = false) Boolean force) {
        findOne0(id);

        MenuSearchParams searchParams = new MenuSearchParams();
        searchParams.setParentId(id);

        if (service.selectCount(searchParams) != 0) {
            throw new HasSubMenuException();
        }

        service.deleteById(id, force);
    }

    @ApiOperation("获取树状菜单结构")
    @GetMapping("/tree")
    public Collection<MenuDTO> tree() {
        return TreeUtils.tree(findAll(null));
    }

}
