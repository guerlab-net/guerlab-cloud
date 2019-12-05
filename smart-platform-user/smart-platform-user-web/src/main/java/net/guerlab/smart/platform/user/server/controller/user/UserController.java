package net.guerlab.smart.platform.user.server.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.exception.PermissionsErrorException;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.exception.UserCannotDeleteException;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.domain.UserModifyDTO;
import net.guerlab.smart.platform.user.server.entity.User;
import net.guerlab.smart.platform.user.server.service.UserService;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

/**
 * 用户
 *
 * @author guer
 */
@Api(tags = "用户")
@RestController("/user/user")
@RequestMapping("/user/user")
public class UserController {

    private UserService service;

    @ApiOperation("查询详情")
    @GetMapping("/{id}")
    public UserDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return findOne0(id).toDTO();
    }

    @ApiOperation("查询列表")
    @GetMapping
    public ListObject<UserDTO> findList(UserSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.queryPage(searchParams));
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public Collection<UserDTO> findAll(UserSearchParams searchParams) {
        return BeanConvertUtils.toList(service.queryAll(searchParams));
    }

    @ApiOperation("添加")
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public UserDTO save(@ApiParam(value = "用户编辑信息", required = true) @RequestBody UserModifyDTO dto) {
        String password = StringUtils.trimToNull(dto.getPassword());

        if (password == null) {
            throw new NeedPasswordException();
        }

        User user = new User();

        BeanUtils.copyProperties(dto, user);

        if (!service.isAdmin(UserContextHandler.getUserId())) {
            user.setAdmin(false);
        }

        service.insertSelective(user);

        return user.toDTO();
    }

    @ApiOperation("编辑")
    @PutMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public UserDTO update(@ApiParam(value = "id", required = true) @PathVariable Long id,
            @ApiParam(value = "用户编辑信息", required = true) @RequestBody UserModifyDTO dto) {
        User user = findOne0(id);

        Long departmentId = dto.getDepartmentId();
        Long nowDepartmentId = user.getDepartmentId();

        BeanUtils.copyProperties(dto, user);

        user.setUserId(id);

        if (Objects.equals(departmentId, nowDepartmentId)) {
            user.setDepartmentId(null);
        } else {
            user.setDepartmentId(departmentId);
            user.setOldDepartmentId(nowDepartmentId);
        }

        if (!service.isAdmin(UserContextHandler.getUserId())) {
            user.setAdmin(null);
        }

        service.updateSelectiveById(user);

        return findOne0(id).toDTO();
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public void delete(@ApiParam(value = "id", required = true) @PathVariable Long id,
            @ApiParam(value = "强制删除标志") @RequestParam(required = false) Boolean force) {
        Long currentUserId = UserContextHandler.getUserId();
        if (Objects.equals(id, currentUserId)) {
            throw new UserCannotDeleteException();
        }

        User user = findOne0(id);

        if (user.getAdmin() && !service.isAdmin(currentUserId)) {
            throw new PermissionsErrorException();
        }

        service.deleteById(id, force);
    }

    private User findOne0(Long id) {
        return service.selectByIdOptional(id).orElseThrow(UserInvalidException::new);
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }
}
