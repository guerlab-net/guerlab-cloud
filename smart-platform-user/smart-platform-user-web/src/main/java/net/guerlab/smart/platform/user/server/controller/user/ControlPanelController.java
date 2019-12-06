package net.guerlab.smart.platform.user.server.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.smart.platform.basic.auth.annotation.IgnoreLogin;
import net.guerlab.smart.platform.basic.auth.enums.TokenType;
import net.guerlab.smart.platform.basic.auth.utils.AbstractJwtHelper;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.commons.domain.TwoFactorAuthenticationBindInfo;
import net.guerlab.smart.platform.commons.exception.*;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.commons.util.TreeUtils;
import net.guerlab.smart.platform.commons.util.TwoFactorAuthentication;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.auth.utils.UserJwtHelper;
import net.guerlab.smart.platform.user.core.domain.MenuDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.entity.IJwtInfo;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.searchparams.*;
import net.guerlab.smart.platform.user.server.domain.LoginRequest;
import net.guerlab.smart.platform.user.server.domain.LoginResponse;
import net.guerlab.smart.platform.user.server.domain.TakeOfficeDataDTO;
import net.guerlab.smart.platform.user.server.domain.UpdatePasswordDTO;
import net.guerlab.smart.platform.user.server.entity.*;
import net.guerlab.smart.platform.user.server.service.*;
import net.guerlab.spring.upload.entity.FileInfo;
import net.guerlab.spring.upload.helper.UploadFileHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 控制面板
 *
 * @author guer
 */
@Slf4j
@Api(tags = "控制面板")
@Transactional(rollbackFor = Exception.class)
@RestController("/user/controlPanel")
@RequestMapping("/user/controlPanel")
public class ControlPanelController {

    private static final String PREFIX =
            UserJwtHelper.PREFIX + AbstractJwtHelper.CONNECTORS + TokenType.SIMPLE_NAME_REFRESH_TOKEN
                    + AbstractJwtHelper.CONNECTORS;

    private UserJwtHelper jwtHelper;

    private UserService service;

    private MenuPermissionService menuPermissionService;

    private MenuService menuService;

    private TakeOfficeService takeOfficeService;

    private DepartmentService departmentService;

    private PositionService positionService;

    private PermissionService permissionService;

    @IgnoreLogin
    @ApiOperation("登录")
    @PostMapping("/login")
    public LoginResponse login(@ApiParam(value = "登录信息", required = true) @RequestBody LoginRequest request) {
        User user = service.findByUsernameOptional(request.getUsername()).orElseThrow(UserNotFindException::new);

        if (!user.getEnabled()) {
            throw new UserUnableException();
        }

        if (service.checkPasswordError(user, request.getPassword())) {
            throw new PasswordErrorException();
        }

        if (!user.getEnableTwoFactorAuthentication()) {
            return login0(user);
        }

        checkTwoFactorAuthentication(request, user);

        return login0(user);
    }

    @IgnoreLogin
    @ApiOperation("动态密码登录")
    @PostMapping("/loginByOpt")
    public LoginResponse loginByOpt(@ApiParam(value = "登录信息", required = true) @RequestBody LoginRequest request) {
        User user = service.findByUsernameOptional(request.getUsername()).orElseThrow(UserNotFindException::new);

        if (!user.getEnabled()) {
            throw new UserUnableException();
        }

        if (!user.getEnableTwoFactorAuthentication()) {
            throw new UnableTwoFactorAuthenticationException();
        }

        checkTwoFactorAuthentication(request, user);

        return login0(user);
    }

    @IgnoreLogin
    @ApiOperation("刷新Token")
    @GetMapping("/refreshToken")
    public LoginResponse refreshToken(
            @ApiParam(value = "refreshToken", required = true) @RequestHeader("refreshToken") String refreshToken) {
        if (!refreshToken.startsWith(PREFIX)) {
            throw new RefreshTokenInvalidException();
        }

        String jwtToken = refreshToken.substring(PREFIX.length());
        IJwtInfo infoFromToken = jwtHelper.parseByRefreshTokenKey(jwtToken);

        return getLoginSucceedDTO(service.selectById(infoFromToken.getUserId()));
    }

    private LoginResponse login0(User user) {
        LoginResponse loginResponse = getLoginSucceedDTO(user);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(user.getUserId());

        User updateInfo = new User();
        updateInfo.setLastLoginTime(LocalDateTime.now());

        service.updateByExampleSelective(updateInfo, service.getExample(searchParams));

        return loginResponse;
    }

    private void checkTwoFactorAuthentication(LoginRequest request, User user) {
        String otp = StringUtils.trimToNull(request.getOtp());

        if (otp == null) {
            throw new OtpInvalidException();
        }

        if (!TwoFactorAuthentication.checkCode(user.getTwoFactorAuthenticationToken(), otp)) {
            throw new TwoFactorAuthenticationFailException();
        }
    }

    @ApiOperation("获取个人信息")
    @GetMapping
    public UserDTO getInfo() {
        return findCurrentUser().toDTO();
    }

    @ApiOperation("获取双因子认证资料")
    @GetMapping("/twoFactorAuthenticationBindInfo")
    public TwoFactorAuthenticationBindInfo getTwoFactorAuthenticationBindInfo() {
        User user = findCurrentUser();

        if (user.getEnableTwoFactorAuthentication()) {
            throw new EnabledTwoFactorAuthenticationException();
        }

        String secretKey = TwoFactorAuthentication.generateSecretKey();
        String qrBarcode = TwoFactorAuthentication.getQrCode(user.getUsername(), secretKey);

        TwoFactorAuthenticationBindInfo bindInfo = new TwoFactorAuthenticationBindInfo();
        bindInfo.setQrBarcode(qrBarcode);
        bindInfo.setSecretKey(secretKey);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(user.getUserId());

        User update = new User();
        update.setEnableTwoFactorAuthentication(false);
        update.setTwoFactorAuthenticationToken(secretKey);
        service.updateByExampleSelective(update, service.getExample(searchParams));

        return bindInfo;
    }

    @ApiOperation("启用双因子认证")
    @PostMapping("/enableTwoFactorAuthentication/{secretKey}")
    public LoginResponse enableTwoFactorAuthentication(
            @ApiParam(value = "双因子认证密钥", required = true) @PathVariable String secretKey,
            @ApiParam(value = "登录信息", required = true) @RequestBody LoginRequest request) {
        User user = findCurrentUser();

        if (user.getEnableTwoFactorAuthentication()) {
            throw new EnabledTwoFactorAuthenticationException();
        } else if (!Objects.equals(user.getTwoFactorAuthenticationToken(), secretKey)) {
            throw new TwoFactorAuthenticationSecretKeyErrorException();
        }

        checkTwoFactorAuthentication(request, user);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(user.getUserId());

        User update = new User();
        update.setEnableTwoFactorAuthentication(true);
        service.updateByExampleSelective(update, service.getExample(searchParams));

        user.setEnableTwoFactorAuthentication(true);

        return login0(user);
    }

    @ApiOperation("禁用双因子认证")
    @PostMapping("/disableTwoFactorAuthentication")
    public LoginResponse disableTwoFactorAuthentication(
            @ApiParam(value = "登录信息", required = true) @RequestBody LoginRequest request) {
        User user = findCurrentUser();

        if (!user.getEnableTwoFactorAuthentication()) {
            throw new UnableTwoFactorAuthenticationException();
        }

        checkTwoFactorAuthentication(request, user);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(user.getUserId());

        User update = new User();
        update.setEnableTwoFactorAuthentication(false);
        update.setTwoFactorAuthenticationToken(Constants.EMPTY_NAME);
        service.updateByExampleSelective(update, service.getExample(searchParams));

        user.setEnableTwoFactorAuthentication(false);
        user.setTwoFactorAuthenticationToken(Constants.EMPTY_NAME);

        return login0(user);
    }

    @ApiOperation("修改密码")
    @PostMapping("/password")
    public void updatePassword(@ApiParam(value = "修改密码信息", required = true) @RequestBody UpdatePasswordDTO dto) {
        String newPassword = StringUtils.trimToNull(dto.getNewPassword());
        String oldPassword = StringUtils.trimToNull(dto.getOldPassword());

        if (newPassword == null) {
            throw new NeedPasswordException();
        }
        if (oldPassword == null) {
            throw new NeedPasswordException();
        }

        User user = findCurrentUser();

        if (service.checkPasswordError(user, oldPassword)) {
            throw new PasswordErrorException();
        }

        service.updatePassword(user.getUserId(), newPassword);
    }

    @ApiOperation("修改个人资料")
    @PostMapping("/profile")
    public void updateProfile(@ApiParam(value = "用户信息", required = true) @RequestBody UserDTO dto) {
        User user = findCurrentUser();

        Long userId = user.getUserId();

        BeanUtils.copyProperties(dto, user);

        setNoUpdateField(user, userId);

        service.updateSelectiveById(user);
    }

    @ApiOperation(value = "头像上传")
    @PostMapping("/avatar")
    public UserDTO avatar(@ApiParam(value = "头像图片文件", required = true) @RequestParam MultipartFile file) {
        User user = findCurrentUser();
        FileInfo fileInfo = UploadFileHelper.upload(file, UserService.DEFAULT_AVATAR_PATH);
        user.setAvatar(fileInfo.getWebPath());
        service.updateSelectiveById(user);
        return user.toDTO();
    }

    private void setNoUpdateField(User user, Long userId) {
        user.setPassword(null);
        user.setUsername(null);
        user.setRegistrationTime(null);
        user.setLastLoginTime(null);
        user.setDepartmentId(null);
        user.setDepartmentName(null);
        user.setUserId(userId);
    }

    private LoginResponse getLoginSucceedDTO(User user) {
        UserDTO dto = user.toDTO();

        LoginResponse loginSucceedDTO = new LoginResponse();
        loginSucceedDTO.setInfo(dto);
        loginSucceedDTO.setAccessToken(jwtHelper.generateByAccessToken(user));
        loginSucceedDTO.setRefreshToken(jwtHelper.generateByRefreshToken(user));
        if (user.getAdmin()) {
            loginSucceedDTO.setPermissionKeys(
                    CollectionUtil.toList(permissionService.selectAll(), Permission::getPermissionKey));
        } else {
            loginSucceedDTO.setPermissionKeys(service.getPermissionKeys(dto.getUserId()));
        }

        return loginSucceedDTO;
    }

    private User findCurrentUser() {
        User user = service.selectByIdOptional(UserContextHandler.getUserId()).orElseThrow(UserInvalidException::new);

        if (!user.getEnabled()) {
            throw new UserUnableException();
        }

        return user;
    }

    @ApiOperation(value = "获取权限关键字列表")
    @GetMapping("/permissionKeys")
    public Collection<String> permissionKeys() {
        return service.getPermissionKeys(UserContextHandler.getUserId());
    }

    @ApiOperation(value = "获取菜单列表")
    @GetMapping("/menus")
    public Collection<MenuDTO> getMenus() {
        Long userId = UserContextHandler.getUserId();
        Collection<Menu> menus;

        if (service.isAdmin(userId)) {
            menus = menuService.selectAll();
        } else {
            Collection<String> permissionKeys = service.getPermissionKeys(userId);
            Collection<Long> menuIds = getMenuIds(permissionKeys);
            menus = getMenus(menuIds);
        }

        return TreeUtils.tree(BeanConvertUtils.toList(menus));
    }

    @ApiOperation(value = "获取职位信息列表")
    @GetMapping("/takeOffice")
    public Collection<TakeOfficeDataDTO> getTakeOffice() {
        Collection<TakeOffice> takeOffices = takeOfficeService.findByUserId(UserContextHandler.getUserId());

        if (CollectionUtil.isEmpty(takeOffices)) {
            return Collections.emptyList();
        }

        Collection<TakeOfficeDataDTO> takeOfficeDTO = BeanConvertUtils.toList(takeOffices, TakeOfficeDataDTO.class);

        Map<Long, List<TakeOfficeDataDTO>> departmentGroup = CollectionUtil
                .group(takeOfficeDTO, TakeOfficeDataDTO::getDepartmentId);
        Map<Long, List<TakeOfficeDataDTO>> positionGroup = CollectionUtil
                .group(takeOfficeDTO, TakeOfficeDataDTO::getPositionId);

        Collection<Department> departments = getDepartments(departmentGroup.keySet());
        Collection<Position> positions = getPositions(positionGroup.keySet());

        for (Department department : departments) {
            List<TakeOfficeDataDTO> takeOfficeList = departmentGroup.get(department.getDepartmentId());

            if (CollectionUtil.isNotEmpty(takeOfficeList)) {
                departmentGroup.get(department.getDepartmentId())
                        .forEach(dto -> dto.setDepartmentName(department.getDepartmentName()));
            }
        }

        for (Position position : positions) {
            List<TakeOfficeDataDTO> takeOfficeList = positionGroup.get(position.getPositionId());

            if (CollectionUtil.isNotEmpty(takeOfficeList)) {
                takeOfficeList.forEach(dto -> dto.setPositionName(position.getPositionName()));
            }
        }

        return takeOfficeDTO;
    }

    private Collection<Department> getDepartments(Collection<Long> departmentIdList) {
        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentIds(departmentIdList);

        return departmentService.selectAll(searchParams);
    }

    private Collection<Position> getPositions(Collection<Long> positionIdList) {
        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setPositionIds(positionIdList);

        return positionService.selectAll(searchParams);
    }

    private Collection<Long> getMenuIds(Collection<String> permissionKeys) {
        if (CollectionUtil.isBlank(permissionKeys)) {
            return Collections.emptyList();
        }

        MenuPermissionSearchParams searchParams = new MenuPermissionSearchParams();
        searchParams.setPermissionKeys(permissionKeys);

        return menuPermissionService.findMenuIdList(searchParams);
    }

    private Collection<Menu> getMenus(Collection<Long> menuIds) {
        if (CollectionUtil.isBlank(menuIds)) {
            return Collections.emptyList();
        }

        MenuSearchParams searchParams = new MenuSearchParams();
        searchParams.setMenuIds(menuIds);

        return menuService.selectAll(searchParams);
    }

    @Autowired
    public void setJwtHelper(UserJwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setMenuPermissionService(MenuPermissionService menuPermissionService) {
        this.menuPermissionService = menuPermissionService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setTakeOfficeService(TakeOfficeService takeOfficeService) {
        this.takeOfficeService = takeOfficeService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
