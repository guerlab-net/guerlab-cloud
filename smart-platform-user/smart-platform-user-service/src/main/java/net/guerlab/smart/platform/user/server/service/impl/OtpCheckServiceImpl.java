package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.util.TwoFactorAuthentication;
import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.smart.platform.user.server.entity.User;
import net.guerlab.smart.platform.user.server.service.OtpCheckService;
import net.guerlab.smart.platform.user.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 双因子检查服务实现
 *
 * @author guer
 */
@Service
public class OtpCheckServiceImpl implements OtpCheckService {

    private UserService userService;

    @Override
    public OtpCheckResponse otpCheck(OtpCheckRequest request) {
        OtpCheckResponse response = new OtpCheckResponse();
        Long userId = request.getUserId();
        String otp = StringUtils.trimToNull(request.getOtp());

        if (!NumberHelper.greaterZero(userId) || otp == null) {
            return response;
        }

        User user = userService.selectById(request.getUserId());

        response.setAccept(user != null && user.getEnableTwoFactorAuthentication() && TwoFactorAuthentication
                .checkCode(user.getTwoFactorAuthenticationToken(), otp));

        return response;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
