package net.guerlab.smart.platform.user.auth.interceptor;

import net.guerlab.smart.platform.basic.auth.enums.TokenType;
import net.guerlab.smart.platform.basic.auth.interceptor.AbstractTokenHandlerInterceptor;
import net.guerlab.smart.platform.basic.auth.utils.AbstractJwtHelper;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.auth.utils.UserJwtHelper;
import net.guerlab.smart.platform.user.core.entity.IJwtInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * jwt token处理
 *
 * @author guer
 */
public class UserJwtTokenHandlerInterceptor extends AbstractTokenHandlerInterceptor {

    private static final String PREFIX =
            UserJwtHelper.PREFIX + AbstractJwtHelper.CONNECTORS + TokenType.SIMPLE_NAME_ACCESS_TOKEN
                    + AbstractJwtHelper.CONNECTORS;

    private UserJwtHelper jwtHelper;

    @Override
    protected boolean accept(String token) {
        return token.startsWith(PREFIX);
    }

    @Override
    protected void setTokenInfo(String token) {
        String jwtToken = token.substring(PREFIX.length());
        IJwtInfo infoFromToken = jwtHelper.parseByAccessTokenKey(jwtToken);

        UserContextHandler.setUserId(infoFromToken.getUserId());
        UserContextHandler.setUsername(infoFromToken.getUsername());
        UserContextHandler.setName(infoFromToken.getName());
        UserContextHandler.setDepartmentId(infoFromToken.getDepartmentId());
        UserContextHandler.setDepartmentName(infoFromToken.getDepartmentName());
    }

    @Autowired
    public void setJwtHelper(UserJwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }
}
