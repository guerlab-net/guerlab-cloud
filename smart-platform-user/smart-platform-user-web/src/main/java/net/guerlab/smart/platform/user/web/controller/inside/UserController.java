package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.TakeOfficeDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.service.TakeOfficeGetHandler;
import net.guerlab.smart.platform.user.service.service.UserService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 用户
 *
 * @author guer
 */
@RestController("/inside/user")
@RequestMapping("/inside/user")
public class UserController {

    private UserService service;

    private TakeOfficeGetHandler takeOfficeGetHandler;

    @GetMapping("/{id}")
    public UserDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return service.selectByIdOptional(id).orElseThrow(UserInvalidException::new).toDTO();
    }

    @PostMapping
    public ListObject<UserDTO> findList(@RequestBody UserSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.queryPage(searchParams));
    }

    @PostMapping("/all")
    public Collection<UserDTO> findAll(@RequestBody UserSearchParams searchParams) {
        return BeanConvertUtils.toList(service.queryAll(searchParams));
    }

    @GetMapping("/{id}/permissionKeys")
    public Collection<String> permissionKeys(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return service.getPermissionKeys(id);
    }

    @GetMapping("/{id}/takeOffice")
    public Collection<TakeOfficeDataDTO> getTakeOffice(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return takeOfficeGetHandler.getTakeOffice(id);
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setTakeOfficeGetHandler(TakeOfficeGetHandler takeOfficeGetHandler) {
        this.takeOfficeGetHandler = takeOfficeGetHandler;
    }
}
