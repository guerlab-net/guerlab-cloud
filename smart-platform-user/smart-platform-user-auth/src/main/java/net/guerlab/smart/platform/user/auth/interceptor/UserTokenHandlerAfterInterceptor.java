package net.guerlab.smart.platform.user.auth.interceptor;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.basic.auth.interceptor.AbstractHandlerInterceptor;
import net.guerlab.smart.platform.commons.exception.AccessTokenInvalidException;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * token后置处理
 *
 * @author guer
 */
public class UserTokenHandlerAfterInterceptor extends AbstractHandlerInterceptor {

    @Override
    protected void preHandle0(HttpServletRequest request, HandlerMethod handlerMethod) {
        if (!NumberHelper.greaterZero(UserContextHandler.getUserId())) {
            throw new AccessTokenInvalidException();
        }
    }
}
