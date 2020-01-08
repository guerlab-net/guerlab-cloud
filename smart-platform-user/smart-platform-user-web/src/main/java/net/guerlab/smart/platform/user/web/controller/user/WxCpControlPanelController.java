package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpOauth2UserInfo;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.basic.auth.annotation.IgnoreLogin;
import net.guerlab.smart.platform.commons.exception.PasswordErrorException;
import net.guerlab.smart.platform.commons.exception.ThirdPartyIdInvalidException;
import net.guerlab.smart.platform.commons.exception.UnsupportedLoginModeException;
import net.guerlab.smart.platform.commons.exception.UserNotFindException;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.auth.utils.UserJwtHelper;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.exception.UserHasBoundException;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.User;
import net.guerlab.smart.platform.user.service.entity.UserOauth;
import net.guerlab.smart.platform.user.service.searchparams.UserOauthSearchParams;
import net.guerlab.smart.platform.user.service.service.UserOauthService;
import net.guerlab.smart.platform.user.service.service.UserService;
import net.guerlab.smart.platform.user.web.domain.BindRequest;
import net.guerlab.smart.platform.user.web.domain.LoginResponse;
import net.guerlab.smart.wx.lib.cp.exception.IsNotCompanyUserException;
import net.guerlab.smart.wx.lib.cp.exception.WxCpCodeEmptyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 企业微信-控制面板
 *
 * @author guer
 */
@Api(tags = "企业微信-控制面板")
@Transactional(rollbackFor = Exception.class)
@RestController("/user/wx/cp/controlPanel")
@RequestMapping("/user/wx/cp/controlPanel")
public class WxCpControlPanelController {

    private static final String OAUTH_TYPE = "WORK_WEIXIN";

    private UserService userService;

    private UserOauthService userOauthService;

    private WxCpService wxCpService;

    private UserJwtHelper jwtHelper;

    @IgnoreLogin
    @ApiOperation("通过code登录")
    @GetMapping("/loginByCode")
    public LoginResponse loginByCode(String code) throws Exception {
        if (StringUtils.isBlank(code)) {
            throw new WxCpCodeEmptyException();
        }

        WxCpOauth2UserInfo userInfo = getWxCpService().getOauth2Service().getUserInfo(code);

        String userId = userInfo.getUserId();

        if (StringUtils.isBlank(userId)) {
            throw new IsNotCompanyUserException();
        }

        return getLoginSucceedDTO(findUser(userId), userId);
    }

    @IgnoreLogin
    @ApiOperation("绑定")
    @PostMapping("/bind")
    public LoginResponse bind(@RequestBody BindRequest bindRequest) {
        String thirdPartyId = StringUtils.trimToNull(bindRequest.getThirdPartyId());
        if (thirdPartyId == null) {
            throw new ThirdPartyIdInvalidException();
        }
        if (findUser(thirdPartyId) != null) {
            throw new UserHasBoundException();
        }

        User user = userService.selectByUsername(bindRequest.getUsername());

        if (user == null) {
            throw new UserNotFindException();
        }

        if (getBind(user.getUserId()) != null) {
            throw new UserHasBoundException();
        }

        if (userService.checkPasswordError(user, bindRequest.getPassword())) {
            throw new PasswordErrorException();
        }

        UserOauth userOauth = new UserOauth();
        userOauth.setUserId(user.getUserId());
        userOauth.setType(OAUTH_TYPE);
        userOauth.setThirdPartyId(thirdPartyId);

        userOauthService.insert(userOauth);

        return getLoginSucceedDTO(user, thirdPartyId);
    }

    @ApiOperation("解绑")
    @PostMapping("/unbind")
    public void bind() {
        Long userId = UserContextHandler.getUserId();

        UserOauth userOauth = new UserOauth();
        userOauth.setUserId(userId);
        userOauth.setType(OAUTH_TYPE);

        userOauthService.delete(userOauth);
    }

    @ApiOperation("获取绑定状态")
    @GetMapping("/bindStatus")
    public boolean bindStatus() {
        return getBind(UserContextHandler.getUserId()) != null;
    }

    private LoginResponse getLoginSucceedDTO(User user, String openId) {
        LoginResponse loginResponse = user == null ? new LoginResponse() : getLoginSucceedDTO(afterLogin(user));

        UserOauth userOauth = new UserOauth();
        userOauth.setUserId(user == null ? null : user.getUserId());
        userOauth.setType(OAUTH_TYPE);
        userOauth.setThirdPartyId(openId);

        loginResponse.setThirdParty(userOauth.toDTO());

        return loginResponse;
    }

    private UserOauth getBind(Long userId) {
        UserOauthSearchParams oauthSearchParams = new UserOauthSearchParams();
        oauthSearchParams.setUserId(userId);
        oauthSearchParams.setType(OAUTH_TYPE);

        return userOauthService.selectOne(oauthSearchParams);
    }

    private User findUser(String openId) {
        UserOauthSearchParams oauthSearchParams = new UserOauthSearchParams();
        oauthSearchParams.setType(OAUTH_TYPE);
        oauthSearchParams.setThirdPartyId(openId);

        UserOauth userOauth = userOauthService.selectOne(oauthSearchParams);

        if (userOauth == null || !NumberHelper.greaterZero(userOauth.getUserId())) {
            return null;
        }

        return userService.selectById(userOauth.getUserId());
    }

    protected User afterLogin(User user) {
        Long userId = user.getUserId();

        LocalDateTime now = LocalDateTime.now();

        User update = new User();
        update.setLastLoginTime(now);
        update.setLastLoginTime(now);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(userId);

        userService.updateByExampleSelective(update, userService.getExample(searchParams));

        return user;
    }

    protected LoginResponse getLoginSucceedDTO(User user) {
        UserDTO dto = user.toDTO();

        LoginResponse loginSucceedDTO = new LoginResponse();
        loginSucceedDTO.setInfo(dto);
        loginSucceedDTO.setAccessToken(jwtHelper.generateByAccessToken(user));
        loginSucceedDTO.setRefreshToken(jwtHelper.generateByRefreshToken(user));

        return loginSucceedDTO;
    }

    private WxCpService getWxCpService() {
        if (wxCpService == null) {
            throw new UnsupportedLoginModeException();
        }
        return wxCpService;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired(required = false)
    public void setWxCpService(WxCpService wxCpService) {
        this.wxCpService = wxCpService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserOauthService(UserOauthService userOauthService) {
        this.userOauthService = userOauthService;
    }

    @Autowired
    public void setJwtHelper(UserJwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }
}
