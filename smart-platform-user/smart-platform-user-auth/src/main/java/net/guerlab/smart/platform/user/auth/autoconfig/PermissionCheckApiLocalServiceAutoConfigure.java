package net.guerlab.smart.platform.user.auth.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.auth.PermissionCheckApi;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.smart.platform.user.server.service.PermissionCheckService;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * @author guer
 */
@Configuration
@Conditional(PermissionCheckApiLocalServiceAutoConfigure.WrapperCondition.class)
public class PermissionCheckApiLocalServiceAutoConfigure {

    @Bean
    public PermissionCheckApi permissionCheckApiLocalServiceWrapper(PermissionCheckService service) {
        return new PermissionCheckApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.server.service.PermissionCheckService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class PermissionCheckApiLocalServiceWrapper implements PermissionCheckApi {

        private PermissionCheckService service;

        @Override
        public PermissionCheckResponse accept(PermissionCheckRequest request) {
            if (request == null) {
                return new PermissionCheckResponse();
            }
            return service.acceptByPermissionKeys(request.getUserId(), request.getPermissionKeys());
        }
    }
}
