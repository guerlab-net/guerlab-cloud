package net.guerlab.smart.platform.user.auth.autoconfig;

import net.guerlab.smart.platform.basic.auth.autoconfig.AbstractAuthInterceptorAutoconfigure;
import net.guerlab.smart.platform.basic.auth.interceptor.AbstractTokenHandlerInterceptor;
import net.guerlab.smart.platform.user.auth.interceptor.UserOptCheckHandlerInterceptor;
import net.guerlab.smart.platform.user.auth.interceptor.UserPermissionCheckHandlerInterceptor;
import net.guerlab.smart.platform.user.auth.interceptor.UserTokenHandlerAfterInterceptor;
import net.guerlab.smart.platform.user.auth.properties.UserAuthProperties;
import net.guerlab.smart.platform.user.auth.properties.UserJwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 鉴权拦截器配置
 *
 * @author guer
 */
@Order
@Configuration
@EnableConfigurationProperties({ UserAuthProperties.class, UserJwtProperties.class })
public class UserAuthInterceptorAutoconfigure extends AbstractAuthInterceptorAutoconfigure<UserAuthProperties> {

    private AbstractTokenHandlerInterceptor[] tokenHandlerInterceptors;

    private UserTokenHandlerAfterInterceptor tokenHandlerAfterInterceptor;

    private UserPermissionCheckHandlerInterceptor permissionCheckHandlerInterceptor;

    private UserOptCheckHandlerInterceptor optCheckHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (AbstractTokenHandlerInterceptor handlerInterceptor : tokenHandlerInterceptors) {
            setPathPatterns(registry.addInterceptor(handlerInterceptor));
        }
        setPathPatterns(registry.addInterceptor(tokenHandlerAfterInterceptor));
        setPathPatterns(registry.addInterceptor(permissionCheckHandlerInterceptor));
        setPathPatterns(registry.addInterceptor(optCheckHandlerInterceptor));
    }

    @Autowired
    public void setTokenHandlerInterceptors(AbstractTokenHandlerInterceptor[] tokenHandlerInterceptors) {
        this.tokenHandlerInterceptors = tokenHandlerInterceptors;
    }

    @Autowired
    public void setTokenHandlerAfterInterceptor(UserTokenHandlerAfterInterceptor tokenHandlerAfterInterceptor) {
        this.tokenHandlerAfterInterceptor = tokenHandlerAfterInterceptor;
    }

    @Autowired
    public void setPermissionCheckHandlerInterceptor(
            UserPermissionCheckHandlerInterceptor permissionCheckHandlerInterceptor) {
        this.permissionCheckHandlerInterceptor = permissionCheckHandlerInterceptor;
    }

    @Autowired
    public void setOptCheckHandlerInterceptor(UserOptCheckHandlerInterceptor optCheckHandlerInterceptor) {
        this.optCheckHandlerInterceptor = optCheckHandlerInterceptor;
    }
}
