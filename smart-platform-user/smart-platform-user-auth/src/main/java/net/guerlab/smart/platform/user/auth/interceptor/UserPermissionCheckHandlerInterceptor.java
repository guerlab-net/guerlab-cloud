package net.guerlab.smart.platform.user.auth.interceptor;

import net.guerlab.smart.platform.basic.auth.interceptor.AbstractHandlerInterceptor;
import net.guerlab.smart.platform.commons.exception.PermissionsErrorException;
import net.guerlab.smart.platform.user.auth.PermissionCheckApi;
import net.guerlab.smart.platform.user.auth.UserContextHandler;
import net.guerlab.smart.platform.user.auth.annotation.HasPermission;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 权限检查拦截器
 *
 * @author guer
 */
public class UserPermissionCheckHandlerInterceptor extends AbstractHandlerInterceptor {

    @Override
    protected void preHandle0(HttpServletRequest request, HandlerMethod handlerMethod) {
        HasPermission hasPermission = getAnnotation(handlerMethod, HasPermission.class);

        if (hasPermission == null) {
            return;
        }

        PermissionCheckRequest permissionCheckRequest = new PermissionCheckRequest();
        permissionCheckRequest.setUserId(UserContextHandler.getUserId());
        permissionCheckRequest.setPermissionKeys(Arrays.asList(hasPermission.value()));

        PermissionCheckResponse permissionCheckResponse = SpringApplicationContextUtil.getContext()
                .getBean(PermissionCheckApi.class).accept(permissionCheckRequest);

        if (!permissionCheckResponse.isAccept()) {
            throw new PermissionsErrorException(permissionCheckResponse.getNotHas());
        }
    }
}
