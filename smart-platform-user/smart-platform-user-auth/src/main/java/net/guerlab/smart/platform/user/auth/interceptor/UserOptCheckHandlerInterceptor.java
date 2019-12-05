package net.guerlab.smart.platform.user.auth.interceptor;

import net.guerlab.smart.platform.basic.auth.interceptor.AbstractHandlerInterceptor;
import net.guerlab.smart.platform.commons.exception.NeedTwoFactorAuthenticationException;
import net.guerlab.smart.platform.commons.exception.TwoFactorAuthenticationFailException;
import net.guerlab.smart.platform.user.auth.OtpCheckApi;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.auth.annotation.NeedTwoFactorAuthentication;
import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 双因子认证检查拦截器
 *
 * @author guer
 */
public class UserOptCheckHandlerInterceptor extends AbstractHandlerInterceptor {

    @Override
    protected void preHandle0(HttpServletRequest request, HandlerMethod handlerMethod) {
        NeedTwoFactorAuthentication needTwoFactorAuthentication = getAnnotation(handlerMethod,
                NeedTwoFactorAuthentication.class);

        if (needTwoFactorAuthentication == null) {
            return;
        }

        String otp = StringUtils.trimToNull(request.getHeader("OTP"));

        if (otp == null) {
            throw new NeedTwoFactorAuthenticationException();
        }

        OtpCheckRequest otpCheckRequest = new OtpCheckRequest();
        otpCheckRequest.setUserId(UserContextHandler.getUserId());
        otpCheckRequest.setOtp(otp);

        OtpCheckResponse otpCheckResponse = SpringApplicationContextUtil.getContext().getBean(OtpCheckApi.class)
                .accept(otpCheckRequest);

        if (!otpCheckResponse.isAccept()) {
            throw new TwoFactorAuthenticationFailException();
        }
    }
}
