package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.PositionDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.domain.UserModifyDTO;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.core.utils.PositionUtils;
import net.guerlab.smart.platform.user.service.entity.User;
import net.guerlab.smart.platform.user.service.service.PositionGetHandler;
import net.guerlab.smart.platform.user.service.service.PositionService;
import net.guerlab.smart.platform.user.service.service.UserService;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

    private PositionGetHandler positionGetHandler;

    private PositionService positionService;

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

    @GetMapping("/{id}/position")
    public Collection<PositionDataDTO> getPosition(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return positionGetHandler.getPosition(id);
    }

    @GetMapping("/{id}/positionKeys")
    public Collection<String> getPositionKeys(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return PositionUtils.getKeys(positionService.findByUserId(id));
    }

    @PostMapping("/add")
    public UserDTO add(@RequestBody UserModifyDTO dto) {
        String password = StringUtils.trimToNull(dto.getPassword());

        if (password == null) {
            throw new NeedPasswordException();
        }

        User user = new User();

        BeanUtils.copyProperties(dto, user);
        user.setAdmin(false);

        service.insertSelective(user);

        return user.toDTO();
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setPositionGetHandler(PositionGetHandler positionGetHandler) {
        this.positionGetHandler = positionGetHandler;
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }
}
