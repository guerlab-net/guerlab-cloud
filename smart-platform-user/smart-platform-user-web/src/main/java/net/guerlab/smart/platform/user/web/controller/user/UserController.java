package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.exception.PermissionsErrorException;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.core.domain.PositionDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.domain.UserModifyDTO;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.exception.UserCannotDeleteException;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.User;
import net.guerlab.smart.platform.user.service.service.PositionGetHandler;
import net.guerlab.smart.platform.user.service.service.UserService;
import net.guerlab.spring.upload.entity.FileInfo;
import net.guerlab.spring.upload.helper.UploadFileHelper;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private PositionGetHandler positionGetHandler;

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
        Long mainDutyId = dto.getMainDutyId();
        Long nowMainDutyId = user.getMainDutyId();

        BeanUtils.copyProperties(dto, user);

        user.setUserId(id);

        if (Objects.equals(departmentId, nowDepartmentId)) {
            user.setDepartmentId(null);
        } else {
            user.setDepartmentId(departmentId);
            user.setOldDepartmentId(nowDepartmentId);
        }
        if (Objects.equals(mainDutyId, nowMainDutyId)) {
            user.setMainDutyId(null);
        } else {
            user.setMainDutyId(mainDutyId);
            user.setOldMainDutyId(nowMainDutyId);
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

    @ApiOperation("删除头像")
    @PostMapping("/{id}/deleteAvatar")
    public UserDTO deleteAvatar(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        service.deleteAvatar(id);
        User user = service.selectById(id);
        return BeanConvertUtils.toObject(user);
    }

    @ApiOperation("头像上传")
    @PostMapping("/uploadAvatar")
    public FileInfo avatar(@ApiParam(value = "头像图片文件", required = true) @RequestParam MultipartFile file) {
        return UploadFileHelper.upload(file, UserService.DEFAULT_AVATAR_PATH);
    }

    @ApiOperation("获取权限关键字列表")
    @GetMapping("/{id}/permissionKeys")
    public Collection<String> permissionKeys(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return service.getPermissionKeys(id);
    }

    @ApiOperation("获取职务信息列表")
    @GetMapping("/{id}/position")
    public Collection<PositionDataDTO> getPosition(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return positionGetHandler.getPosition(id);
    }

    private User findOne0(Long id) {
        return service.selectByIdOptional(id).orElseThrow(UserInvalidException::new);
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setPositionGetHandler(PositionGetHandler positionGetHandler) {
        this.positionGetHandler = positionGetHandler;
    }
}
